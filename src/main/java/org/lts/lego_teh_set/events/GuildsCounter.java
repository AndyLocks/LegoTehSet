package org.lts.lego_teh_set.events;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.lts.lego_teh_set.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/// Updates the bot status and writes the number of guilds in which the bot is located
@Component
public class GuildsCounter extends ListenerAdapter {

    private final Bot bot;

    @Autowired
    public GuildsCounter(@Lazy Bot bot) {
        this.bot = bot;
    }

    private void updateGuildsLengthActivity() {
        bot.getShard().setActivity(Activity.watching(String.format("%s Guilds", bot.getShard()
                                .getGuilds().size())));
    }

    @Override
    public void onReady(ReadyEvent event) {
        this.updateGuildsLengthActivity();
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        this.updateGuildsLengthActivity();
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        this.updateGuildsLengthActivity();
    }

}
