package lego_teh_set_discord_bot.context_menus;

import lego_teh_set_discord_bot.component_creators.EmbedBuilderCreator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import rebrickableAPI.returned_objects.Set;

import rebrickableAPI.RebrickableAPIGetter;

import java.util.List;

public class ContextMenuSet extends ListenerAdapter {

    private final RebrickableAPIGetter rebrickableAPIGetter = new RebrickableAPIGetter();

    @Override
    public void onMessageContextInteraction(MessageContextInteractionEvent event) {
        if(event.getName().equals("set")) {
            String search = event.getTarget().getContentDisplay();


            List<Set> setList = this.rebrickableAPIGetter.getSearchResult(search);
            if (setList.isEmpty()) {
                EmbedBuilder nullError = EmbedBuilderCreator.Companion.getNullErrorEmbed();
                event.replyEmbeds(nullError.build()).setEphemeral(true).queue();
                return;
            }

            EmbedBuilder embed = EmbedBuilderCreator.Companion.getEmbedBuilder(setList.get(0));
            Button linkButton = Button.link(setList.get(0).getSetUrl(), "Set on Rebrickable");

            event.replyEmbeds(embed.build()).addActionRow(linkButton).queue();
        }
    }
}
