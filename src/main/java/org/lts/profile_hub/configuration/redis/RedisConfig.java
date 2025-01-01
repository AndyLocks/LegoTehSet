package org.lts.profile_hub.configuration.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.util.function.Function;

@Configuration
public class RedisConfig {


    /// Function for generating a global key prefix for the whole application
    ///
    /// ### Example
    ///
    /// ```
    /// lts:.profile_hub:...
    ///```
    ///
    /// In this case `...` is continuations of the global key.
    /// This function should be composed with others to produce more accurate keys.
    ///
    /// #### Example
    ///
    /// ```java
    /// public static final Function<String, String> ABSTRACT_CONTAINER_KEY_GENERATOR =
    ///     CacheConfig.GLOBAL_KEY_GENERATOR.compose(s -> String.format("container:%s", s));
    /// ```
    ///
    /// Result:
    ///
    /// ```
    /// lts:.profile_hub:user:<DISCORD_USER_ID>
    /// ```
    public static final Function<String, String> GLOBAL_KEY_GENERATOR =
            s -> String.format("lts:.profile_hub:%s", s);

    @Value("${redis.host}")
    private String redisServer;

    @Value("${redis.port}")
    private int redisPort;

    private final Logger LOG = LoggerFactory.getLogger(RedisConfig.class);

    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {
        LOG.debug("Redis server: {}", redisServer);
        LOG.debug("Redis port: {}", redisPort);

        var configuration = new RedisStandaloneConfiguration(redisServer, redisPort);
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
                        .enableTimeToIdle())
                .build();
    }

}
