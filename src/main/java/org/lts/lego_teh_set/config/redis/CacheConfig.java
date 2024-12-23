package org.lts.lego_teh_set.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.time.Duration;
import java.util.function.Function;

@Configuration
@EnableCaching
public class CacheConfig {

    /// Function for generating a global key prefix for the whole application
    ///
    /// ### Example
    ///
    /// ```
    /// lts:...
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
    /// lts:container:3494023849908989899
    /// ```
    ///
    /// # Implementations example
    ///
    /// - {@link AbstractContainerTemplateConfig#ABSTRACT_CONTAINER_KEY_GENERATOR}
    /// - {@link SetRedisTemplateConfig#SET_KEY_GENERATOR}
    public static final Function<String, String> GLOBAL_KEY_GENERATOR = s -> String.format("lts:%s", s);

    @Value("${redis.host}")
    private String redisServer;

    @Value("${redis.port}")
    private int redisPort;

    private final Logger LOG = LoggerFactory.getLogger(CacheConfig.class);

    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {

        LOG.debug("Redis server: {}", redisServer);
        LOG.debug("Redis port: {}", redisPort);

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisServer, redisPort);
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(5))
                        .enableTimeToIdle())
                .build();
    }

}
