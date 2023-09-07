package lego_teh_set_discord_bot.commands;

import lego_teh_set_discord_bot.component_creators.EmbedBuilderCreator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import net.dv8tion.jda.api.interactions.components.buttons.Button;
import rebrickableAPI.RebrickableAPIGetter;
import rebrickableAPI.returned_objects.Set;

import java.util.List;

public class CommandSet extends ListenerAdapter {

    private final RebrickableAPIGetter rebrickableAPIGetter = new RebrickableAPIGetter();

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("set")) {
            OptionMapping optionMapping = event.getOption("request");
            String search = optionMapping.getAsString();

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
