package org.lts.lego_teh_set.config;

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
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

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
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        var redisTemplate = new RedisTemplate<byte[], byte[]>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        return redisTemplate;
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
