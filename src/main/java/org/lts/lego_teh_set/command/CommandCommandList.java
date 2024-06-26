package org.lts.lego_teh_set.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.lts.lego_teh_set.component_creators.EmbedBuilderCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for listening to the command "command_list".
 * Outputs a list of all commands to the chat.
 * <p>
 * This class is for {@link net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder}.
 * This class implements {@link #onSlashCommandInteraction(SlashCommandInteractionEvent)}.
 */
public class CommandCommandList extends ListenerAdapter {

    private final Logger LOGGER = LoggerFactory.getLogger(CommandCommandList.class);

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("command_list")) {
            LOGGER.info("Command list command entry point");

            event.replyEmbeds(
                    EmbedBuilderCreator.getCommandListEmbedBuilder().build()
            ).queue();
        }
    }

}
