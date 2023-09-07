package lego_teh_set_discord_bot.commands;

import lego_teh_set_discord_bot.component_creators.EmbedBuilderCreator;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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
