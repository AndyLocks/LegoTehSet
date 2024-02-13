package lego_teh_set_discord_bot.commands;

import lego_teh_set_discord_bot.component_creators.EmbedBuilderCreator;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Class for listening to the command "command_list".
 * Outputs a list of all commands to the chat.
 * <p>
 * This class is for {@link net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder}.
 * This class implements {@link #onSlashCommandInteraction(SlashCommandInteractionEvent)}.
 */
public class CommandCommandList extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("command_list")) {

            event.replyEmbeds(
                    EmbedBuilderCreator.Companion.getCommandListEmbedBuilder().build()
            ).queue();
        }
    }
}
