package lego_teh_set_discord_bot.commands;

import lego_teh_set_discord_bot.component_creators.EmbedBuilderCreator;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import rebrickableAPI.RebrickableAPIGetter;
import rebrickableAPI.Theme;
import rebrickableAPI.exceptions.InvalidThemeId;
import rebrickableAPI.returned_objects.Set;
import spring_config.SpringConfig;

/**
 * Класс для прослушки команды random
 * <p>
 * Этот класс нужен для {@link net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder}
 * Этот класс реализует {@link #onSlashCommandInteraction(SlashCommandInteractionEvent)}
 */
public class CommandRandom extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("random")) {
            OptionMapping optionMapping = event.getOption("theme");
            Theme theme = null;
            try{
                int themeId = optionMapping.getAsInt();
                theme = Theme.getThemeFromId(themeId);
            }
            catch (NullPointerException ignored) {

            }
            catch (InvalidThemeId ignored) {

            }

            Set set;
            try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class)) {
                if(theme == null) {
                    set = context.getBean("rebrickableAPIGetter", RebrickableAPIGetter.class).getRundomSet();
                }
                else
                    set = context.getBean("rebrickableAPIGetter", RebrickableAPIGetter.class).getRundomSet(theme);
            }

            event.replyEmbeds(
                    EmbedBuilderCreator.Companion.getEmbedBuilder(set).build()
            ).addActionRow(Button.link(set.getSetUrl(), "Set on Rebrickable")).queue();
        }
    }
}
