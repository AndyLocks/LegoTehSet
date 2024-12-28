package org.lts.lego_teh_set.config.redis;

import org.lts.lego_teh_set.commands.container.AbstractContainer;
import org.lts.lego_teh_set.commands.container.AbstractContainerRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.function.Function;

@Configuration
public class AbstractContainerTemplateConfig {

    /// The time after which container records are deleted in redis
    public static final Duration TTL_DURATION = Duration.ofMinutes(5);

    /// Function for generating a key prefix
    ///
    /// ### Example
    ///
    /// ```
    /// lts:container:3494023849908989899
    /// ```
    ///
    /// In this case `lts` is a global key prefix: {@link CacheConfig#GLOBAL_KEY_GENERATOR}
    ///
    /// @see CacheConfig#GLOBAL_KEY_GENERATOR
    public static final Function<String, String> ABSTRACT_CONTAINER_KEY_GENERATOR =
            CacheConfig.GLOBAL_KEY_GENERATOR.compose(s -> String.format("container:%s", s));

    @Bean
    public RedisTemplate<String, AbstractContainer> abstractContainerRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

        var redisTemplate = new RedisTemplate<String, AbstractContainer>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(new AbstractContainerRedisSerializer());

        return redisTemplate;
    }

}
