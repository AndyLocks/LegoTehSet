package lego_teh_set_discord_bot.evenst;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildsBearbeiter extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {

        for (Guild guild : event.getJDA().getGuilds()){

            if (guild.getName().equals("Наш гнев, лучше не суммировать")) {
                guild.leave().queue();
            }
        }

        for (Guild guild : event.getJDA().getGuilds()) {

            System.out.println(guild.getName());
        }
    }
}
