package org.lts.lego_teh_set.commands.implementations;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.lts.lego_teh_set.commands.CommandDataSupplier;
import org.lts.lego_teh_set.commands.OptionDates;
import org.lts.lego_teh_set.commands.SlashCommands;
import org.lts.lego_teh_set.commands.EmbedBuilderCreator;
import org.lts.lego_teh_set.rebrickableAPI.Theme;
import org.lts.lego_teh_set.rebrickableAPI.exceptions.InvalidThemeId;
import org.lts.lego_teh_set.repository.SetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/// Class for listening to the command `random`.
///
/// Returns a random set.
///
/// This class implements:
///
/// - {@link #onSlashCommandInteraction(SlashCommandInteractionEvent)}.
@Component
public class CommandRandom extends ListenerAdapter implements CommandDataSupplier {

    private final SetRepository setRepository;
    private final Logger LOG = LoggerFactory.getLogger(CommandRandom.class);

    @Autowired
    public CommandRandom(final SetRepository setRepository) {
        this.setRepository = setRepository;
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(SlashCommands.RANDOM.getName(), SlashCommands.RANDOM.getDescription())
                .addOptions(OptionDates.THEME.getOptionData());
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals(SlashCommands.RANDOM.getName())) return;

        var theme = Optional.ofNullable(event.getOption("theme"))
                .map(optionMapping -> {
                            try {
                                return Theme.fromId(optionMapping.getAsInt());
                            } catch (InvalidThemeId _) {
                                return null;
                            }
                        }
                ).orElse(null);

        var set = theme == null ? setRepository.getRandomSet() : setRepository.getRandomSet(theme);

        LOG.debug("Random command entry point with theme: {} and lego set: {} : {}", theme, set.name(),
                set.parts());

        event.replyEmbeds(EmbedBuilderCreator.fromSet(set).build())
                .addActionRow(Button.link(set.setUrl(), "Set on Rebrickable")).queue();
    }

}
