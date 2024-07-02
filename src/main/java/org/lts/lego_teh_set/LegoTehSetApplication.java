package org.lts.lego_teh_set;

import org.lts.lego_teh_set.command.CommandCommandList;
import org.lts.lego_teh_set.command.CommandRandom;
import org.lts.lego_teh_set.command.search.CommandSearch;
import org.lts.lego_teh_set.evenst.GuildsCounter;
import org.lts.lego_teh_set.evenst.SlashCommandUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

@SpringBootApplication
public class LegoTehSetApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(LegoTehSetApplication.class);

	private static ShardManager shard;

	public static void main(String[] args) {
		SpringApplication.run(LegoTehSetApplication.class, args);
		createAndStartBot();
	}

	private static void createAndStartBot() {
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
				GatewayIntent.SCHEDULED_EVENTS);

		bot.addEventListeners(
                new GuildsCounter(),
				new CommandCommandList(),
				new CommandRandom(),
				new CommandSearch());

		bot.addEventListeners(
				new SlashCommandUpdater());

		bot.setMemberCachePolicy(MemberCachePolicy.ALL);
		bot.setChunkingFilter(ChunkingFilter.ALL);
		bot.enableCache(CacheFlag.ONLINE_STATUS);

		shard = bot.build();
	}

	public static ShardManager getShard() {
		return shard;
	}
}
