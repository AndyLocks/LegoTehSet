package org.lts.lego_teh_set.commands.implementations;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.lts.lego_teh_set.commands.CommandDataSupplier;
import org.lts.lego_teh_set.commands.OptionDates;
import org.lts.lego_teh_set.commands.SlashCommands;
import org.lts.lego_teh_set.commands.container.AbstractContainer;
import org.lts.lego_teh_set.commands.container.implementations.SearchContainer;
import org.lts.lego_teh_set.commands.EmbedBuilderCreator;
import org.lts.lego_teh_set.config.redis.AbstractContainerTemplateConfig;
import org.lts.lego_teh_set.rebrickableAPI.OrderingType;
import org.lts.lego_teh_set.repository.SetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/// A class to listen to the `search` command.
///
/// Outputs to chat a list of all found sets by request.
///
/// This class implements:
///
/// - {@link #onSlashCommandInteraction(SlashCommandInteractionEvent)},
@Component
public class CommandSearch extends ListenerAdapter implements CommandDataSupplier {

    private final Logger LOGGER = LoggerFactory.getLogger(CommandSearch.class);
    private final SetRepository setRepository;
    private final RedisTemplate<String, AbstractContainer> abstractContainerRedisTemplate;

    @Autowired
    public CommandSearch(final SetRepository setRepository, final RedisTemplate<String, AbstractContainer> abstractContainerRedisTemplate) {
        this.setRepository = setRepository;
        this.abstractContainerRedisTemplate = abstractContainerRedisTemplate;
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(SlashCommands.SEARCH.getName(), SlashCommands.SEARCH.getDescription())
                .addOptions(OptionDates.REQUEST.getOptionData(), OptionDates.ORDERING_TYPE.getOptionData());
    }

    /// Discord search command entry point.
    ///
    /// @param event slash command event from discord
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals(SlashCommands.SEARCH.getName())) return;

        var request = event.getOption("request").getAsString();

        var orderingType = OrderingType.fromJsonProperty(event.getOption("ordering") == null ? null : event.getOption("ordering").getAsString());

        LOGGER.debug("Search command entry point with request {} and {} ordering type", request, orderingType);

        var sets = setRepository.search(request, orderingType);

        if (sets.isEmpty()) {
            event.replyEmbeds(EmbedBuilderCreator.getNullErrorEmbedBuilder().build()).setEphemeral(true).queue();
            return;
        }

        var key = AbstractContainerTemplateConfig.ABSTRACT_CONTAINER_KEY_GENERATOR
                .apply(event.getInteraction().getId());
        var container = SearchContainer.fromSetList(sets.stream().toList());

        abstractContainerRedisTemplate.opsForValue().set(key, container, AbstractContainerTemplateConfig.TTL_DURATION);

        event.replyEmbeds(container.currentEmbed().toEmbedBuilder().build())
                .addActionRow(container.buttons())
                .queue();
    }

}
