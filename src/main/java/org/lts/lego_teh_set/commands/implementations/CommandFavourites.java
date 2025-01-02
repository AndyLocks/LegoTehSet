package org.lts.lego_teh_set.commands.implementations;

import io.grpc.StatusRuntimeException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.lts.Api;
import org.lts.ProfileHubGrpc;
import org.lts.lego_teh_set.commands.CommandDataSupplier;
import org.lts.lego_teh_set.commands.SlashCommands;
import org.lts.lego_teh_set.commands.container.AbstractContainer;
import org.lts.lego_teh_set.commands.container.implementations.CacheableLazyFavouritesContainer;
import org.lts.lego_teh_set.config.redis.AbstractContainerTemplateConfig;
import org.lts.lego_teh_set.repository.SetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/// Class for listening to the command `favourites`.
///
/// Shows all favourite sets
///
/// This class implements:
///
/// - [ListenerAdapter#onSlashCommandInteraction(SlashCommandInteractionEvent].
@Component
public class CommandFavourites extends ListenerAdapter implements CommandDataSupplier {

    private final ProfileHubGrpc.ProfileHubBlockingStub profileHubBlockingStub;
    private final RedisTemplate<String, AbstractContainer> abstractContainerRedisTemplate;
    private final SetRepository setRepository;
    private final Logger LOG = LoggerFactory.getLogger(CommandFavourites.class);

    @Autowired
    public CommandFavourites(ProfileHubGrpc.ProfileHubBlockingStub profileHubBlockingStub, RedisTemplate<String, AbstractContainer> abstractContainerRedisTemplate, SetRepository setRepository) {
        this.profileHubBlockingStub = profileHubBlockingStub;
        this.abstractContainerRedisTemplate = abstractContainerRedisTemplate;
        this.setRepository = setRepository;
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(SlashCommands.FAVOURITES.getName(),
                SlashCommands.FAVOURITES.getDescription());
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals(SlashCommands.FAVOURITES.getName())) return;

        var discordUserId = event.getUser().getId();

        List<Api.FavouriteSet> sets = null;
        try {
            sets = profileHubBlockingStub.getAllFavourite(Api.DiscordUser.newBuilder()
                            .setId(discordUserId)
                            .build())
                    .getSetList();
        } catch (StatusRuntimeException e) {
            event.reply("Error: " + e.getMessage()).setEphemeral(true).queue();
            LOG.error("Error during communication with [ProfileHub]", e);
            return;
        }

        if (sets == null || sets.isEmpty()) {
            event.reply("Nothing here").setEphemeral(true).queue();
            return;
        }

        var key = AbstractContainerTemplateConfig.ABSTRACT_CONTAINER_KEY_GENERATOR
                .apply(event.getInteraction().getId());
        var container = CacheableLazyFavouritesContainer.fromSetList(event.getMember() == null ? event.getUser().getEffectiveName() : event.getMember().getNickname(), sets, setRepository);

        abstractContainerRedisTemplate.opsForValue().set(key, container, AbstractContainerTemplateConfig.TTL_DURATION);

        event.replyEmbeds(container.currentEmbed().toEmbedBuilder().build())
                .addActionRow(container.buttons())
                .queue();
    }

}
