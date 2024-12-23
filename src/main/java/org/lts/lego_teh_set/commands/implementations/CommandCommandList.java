package org.lts.lego_teh_set.commands.implementations;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.lts.lego_teh_set.commands.CommandDataSupplier;
import org.lts.lego_teh_set.commands.SlashCommands;
import org.lts.lego_teh_set.commands.EmbedBuilderCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/// Class for listening to the command `command_list`.
///
/// Outputs a list of all commands to the chat.
///
/// This class implements:
///
/// - {@link #onSlashCommandInteraction(SlashCommandInteractionEvent)}.
@Component
public class CommandCommandList extends ListenerAdapter implements CommandDataSupplier {

    private final Logger LOGGER = LoggerFactory.getLogger(CommandCommandList.class);

    @Override
    public CommandData getCommandData() {
        return Commands.slash(SlashCommands.COMMAND_LIST.getName(),
                SlashCommands.COMMAND_LIST.getDescription());
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals(SlashCommands.COMMAND_LIST.getName())) return;

        LOGGER.debug("Command list command entry point");

        event.replyEmbeds(EmbedBuilderCreator.getCommandListEmbedBuilder().build())
                .queue();
    }

}
