package lego_teh_set_discord_bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class TestCommandAboba extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if(event.getName().equals("aboba")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setDescription(event.getInteraction().getId());
            event.replyEmbeds(embedBuilder.build()).addActionRow(
                    Button.danger("abobaButton", "aboba")
            ).queue();

        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {

        event.replyEmbeds(
                new EmbedBuilder().setDescription(event.getMessage().getInteraction().getId()).build()
        ).queue();
    }
}
