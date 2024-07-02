package org.lts.lego_teh_set.evenst;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.lts.lego_teh_set.LegoTehSetApplication;

public class GuildsCounter extends ListenerAdapter {

    private void updateGuildsLengthActivity() {
        LegoTehSetApplication.getShard().setActivity(
                Activity.watching(
                        String.format("%s Guilds", LegoTehSetApplication.getShard()
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
