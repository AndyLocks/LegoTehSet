package lego_teh_set_discord_bot.commands;

import lego_teh_set_discord_bot.component_creators.EmbedBuilderCreator;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import rebrickableAPI.RebrickableAPIGetter;
import rebrickableAPI.returned_objects.Set;

import java.util.ArrayList;
import java.util.List;

public class CommandSetList extends ListenerAdapter {
    private static final int maxPageNum = 4372;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("set_list")) {
            OptionMapping optionMappingPageNum = event.getOption("page_num");
            int pageNum;
            try{
                pageNum = optionMappingPageNum.getAsInt();
            }
            catch (NullPointerException e) {
                pageNum = 1;
            }
            catch (ArithmeticException e) {
                event.reply("Arithmetic error").setEphemeral(true).queue();
                return;
            }
            catch (Exception e) {
                event.reply("Error").setEphemeral(true).queue();
                return;
            }

            if(pageNum <= 0) {
                event.reply("The number must be bigger than zero").setEphemeral(true).queue();
                return;
            }
            if(pageNum > maxPageNum) {
                event.reply("The number is too big").setEphemeral(true).queue();
                return;
            }

            List<MessageEmbed> setEmbedBuilderList = new ArrayList<>();
            for(Set set : new RebrickableAPIGetter().getPageWithFiveSets(pageNum)) {
                setEmbedBuilderList.add(EmbedBuilderCreator.Companion.getEmbedBuilder(set).build());
            }

            event.reply("# Page: " + pageNum).addEmbeds(setEmbedBuilderList).queue();
        }
    }
}
