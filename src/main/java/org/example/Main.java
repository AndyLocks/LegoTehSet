package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import lego_teh_set_discord_bot.commands.CommandCommandList;
import lego_teh_set_discord_bot.commands.CommandSet;
import lego_teh_set_discord_bot.commands.full_search.CommandFullSearch;
import lego_teh_set_discord_bot.evenst.GuildsCounter;
import lego_teh_set_discord_bot.evenst.SlashCommandUpdater;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.api.sharding.ShardManager;

public class Main {

    private static ShardManager shard;

    public static void main(String[] args) {
        Dotenv config = Dotenv.configure().load();
        String token = config.get("TOKEN");

        DefaultShardManagerBuilder bot = DefaultShardManagerBuilder.create(
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
                GatewayIntent.SCHEDULED_EVENTS
        );

        bot.addEventListeners(
                new GuildsCounter(),
                new CommandSet(),
                new CommandCommandList(),
                new CommandFullSearch()
        );

        bot.addEventListeners(
                new SlashCommandUpdater()
        );


        bot.setMemberCachePolicy(MemberCachePolicy.ALL);
        bot.setChunkingFilter(ChunkingFilter.ALL);
        bot.enableCache(CacheFlag.ONLINE_STATUS);

        shard = bot.build();
    }

    public static ShardManager getShard() {
        return shard;
    }
}