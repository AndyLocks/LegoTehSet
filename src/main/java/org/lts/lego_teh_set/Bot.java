package org.lts.lego_teh_set;

import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.api.hooks.EventListener;

import java.util.Collection;

/// Discord Bot
public class Bot {

    private ShardManager shard;

    private final String token;
    private final Collection<? extends EventListener> listenerAdapters;

    public Bot(String token, Collection<? extends EventListener> listenerAdapters) {
        this.token = token;
        this.listenerAdapters = listenerAdapters;
    }

    /// Creates and starts bot
    public void start() {
        var bot = DefaultShardManagerBuilder.create(
                token,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.AUTO_MODERATION_CONFIGURATION,
                GatewayIntent.AUTO_MODERATION_EXECUTION,
                GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                GatewayIntent.DIRECT_MESSAGE_TYPING,
                GatewayIntent.DIRECT_MESSAGES,
                GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                GatewayIntent.GUILD_INVITES,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.GUILD_MESSAGE_TYPING,
                GatewayIntent.GUILD_MODERATION,
                GatewayIntent.GUILD_PRESENCES,
                GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.SCHEDULED_EVENTS);

        bot.addEventListeners(listenerAdapters.toArray());

        bot.setMemberCachePolicy(MemberCachePolicy.ALL);
        bot.setChunkingFilter(ChunkingFilter.ALL);
        bot.enableCache(CacheFlag.ONLINE_STATUS);

        shard = bot.build();
    }

    public ShardManager getShard() {
        return shard;
    }

}
