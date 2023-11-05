package lego_teh_set_discord_bot.evenst

import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.example.Main

/**
 * Считает количество гильдий, на которых находится бот.
 */
class GuildsCounter : ListenerAdapter() {

    private fun updateGuildsLengthActivity() {

        Main.getShard().setActivity(Activity.watching("${Main.getShard().guilds.size} Guilds"));
    }

    override fun onReady(event: ReadyEvent) {

        this.updateGuildsLengthActivity()
    }

    override fun onGuildJoin(event: GuildJoinEvent) {

        this.updateGuildsLengthActivity()
    }

    override fun onGuildLeave(event: GuildLeaveEvent) {

        this.updateGuildsLengthActivity()
    }
}