package org.lts.lego_teh_set.config.redis;

import org.lts.lego_teh_set.rebrickableAPI.dto.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.function.Function;

@Configuration
public class SetRedisTemplateConfig {

    /// The time after which set records are deleted in redis
    public static final Duration TTL_DURATION = Duration.ofMinutes(15);

    /// Function for generating a key prefix
    ///
    /// ### Example
    ///
    /// ```
    /// lts:set:3494023849908989899
    /// ```
    ///
    /// In this case `lts` is a global key prefix: {@link CacheConfig#GLOBAL_KEY_GENERATOR}
    ///
    /// @see CacheConfig#GLOBAL_KEY_GENERATOR
    public static final Function<String, String> SET_LIST_KEY_GENERATOR =
            CacheConfig.GLOBAL_KEY_GENERATOR.compose(s -> String.format("set_list:%s", s));

    public static final Function<String, String> SET_KEY_GENERATOR =
            CacheConfig.GLOBAL_KEY_GENERATOR.compose(s -> String.format("set:%s", s));


    @Bean
    public RedisTemplate<String, Set> setRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

        var redisTemplate = new RedisTemplate<String, Set>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());

        return redisTemplate;
    }

}
