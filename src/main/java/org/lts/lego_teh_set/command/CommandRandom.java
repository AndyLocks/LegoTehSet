package org.lts.lego_teh_set.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.lts.lego_teh_set.ApplicationContextProvider;
import org.lts.lego_teh_set.component_creators.EmbedBuilderCreator;
import org.lts.lego_teh_set.rebrickableAPI.Theme;
import org.lts.lego_teh_set.rebrickableAPI.exceptions.InvalidThemeId;
import org.lts.lego_teh_set.rebrickableAPI.returned_objects.Set;
import org.lts.lego_teh_set.repository.SetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for listening to the command "random".
 * Returns a random set.
 * <p>
 * This class is for
 * {@link net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder}.
 * This class implements
 * {@link #onSlashCommandInteraction(SlashCommandInteractionEvent)}.
 */
public class CommandRandom extends ListenerAdapter {

    private SetRepository setRepository = ApplicationContextProvider.getApplicationContext()
            .getBean("setRepository", SetRepository.class);
    private final Logger LOGGER = LoggerFactory.getLogger(CommandRandom.class);

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("random")) {
            OptionMapping optionMapping = event.getOption("theme");
            Theme theme = null;
            try {
                int themeId = optionMapping.getAsInt();
                theme = Theme.getThemeFromId(themeId);
            } catch (NullPointerException ignored) {

            } catch (InvalidThemeId _) {
            }

            Set set;
            if (theme == null) {
                set = this.setRepository.getRandomSet();
            } else
                set = this.setRepository.getRandomSet(theme);

            LOGGER.info("Random command entry point with theme: {} and lego set: {} : {}", theme, set.getName(),
                    set.getNumParts());

            event.replyEmbeds(
                            EmbedBuilderCreator.getEmbedBuilder(set).build())
                    .addActionRow(Button.link(set.getSetUrl(), "Set on Rebrickable")).queue();
        }
    }

}
