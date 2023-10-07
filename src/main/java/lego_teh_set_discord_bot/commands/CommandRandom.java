package lego_teh_set_discord_bot.commands;

import lego_teh_set_discord_bot.component_creators.EmbedBuilderCreator;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import rebrickableAPI.RebrickableAPIGetter;
import rebrickableAPI.returned_objects.Set;

public class CommandRandom extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("random")) {
            Set set = new RebrickableAPIGetter().getRundomSet();
            event.replyEmbeds(
                    EmbedBuilderCreator.Companion.getEmbedBuilder(set).build()
            ).addActionRow(Button.link(set.getSetUrl(), "Set on Rebrickable")).queue();
        }
    }
}
