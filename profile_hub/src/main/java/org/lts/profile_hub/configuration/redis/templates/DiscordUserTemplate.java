package org.lts.profile_hub.configuration.redis.templates;

import org.lts.profile_hub.configuration.redis.RedisConfig;
import org.lts.profile_hub.entity.dto.DiscordUserDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.function.Function;

@Configuration
public class DiscordUserTemplate {

    /// The time after which container records are deleted in redis
    public static final Duration TTL_DURATION = Duration.ofMinutes(5);

    /// Function for generating a key prefix
    ///
    /// ### Example
    ///
    /// ```
    /// lts:.profile_hub:user:<DISCORD_USER_ID>
    /// ```
    ///
    /// In this case `lts:.profile_hub:...` is a global key prefix: {@link RedisConfig#GLOBAL_KEY_GENERATOR}
    ///
    /// @see RedisConfig#GLOBAL_KEY_GENERATOR
    public static final Function<String, String> USER_KEY_GENERATOR =
            RedisConfig.GLOBAL_KEY_GENERATOR.compose(s -> String.format("user:%s", s));

    @Bean
    public RedisTemplate<String, DiscordUserDto> userRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

        var redisTemplate = new RedisTemplate<String, DiscordUserDto>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());

        return redisTemplate;
    }

}
