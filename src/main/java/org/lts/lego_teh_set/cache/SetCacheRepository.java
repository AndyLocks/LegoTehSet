package org.lts.lego_teh_set.cache;

import org.lts.lego_teh_set.rebrickableAPI.returned_objects.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SetCacheRepository {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    private final Logger LOG = LoggerFactory.getLogger(SetCacheRepository.class);

    /**
     * Caches a list of sets by key
     *
     * @param key  must not be null
     * @param sets must not be null
     */
    public void write(String key, List<Set> sets) {

        LOG.info("Writing cache with key {} and sets {}", key, sets);
        redisTemplate.opsForValue().set(key, sets);
    }

    /**
     * Loads list of sets from cache by key
     *
     * @param key must not be null
     * @return null when key does not exist
     */
    public List<Set> load(String key) {

        LOG.info("Loading cache with key {}", key);
        return (List<Set>) redisTemplate.opsForValue().get(key);
    }

    /**
     * Deletes cache by key
     *
     * @param key must not be null
     */
    public void delete(String key) {

        LOG.info("Deleting cache with key {}", key);
        redisTemplate.delete(key);
    }

}
