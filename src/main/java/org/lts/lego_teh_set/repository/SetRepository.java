package org.lts.lego_teh_set.repository;

import org.lts.lego_teh_set.config.redis.SetRedisTemplateConfig;
import org.lts.lego_teh_set.rebrickableAPI.OrderingType;
import org.lts.lego_teh_set.rebrickableAPI.RebrickableAPIRepository;
import org.lts.lego_teh_set.rebrickableAPI.Theme;
import org.lts.lego_teh_set.rebrickableAPI.dto.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/// Interacts with {@link RebrickableAPIRepository} and also caches data
@Repository
public class SetRepository {

    private final Logger LOG = LoggerFactory.getLogger(SetRepository.class);
    private final RebrickableAPIRepository rebrickableAPIGetter;
    private final RedisTemplate<String, Set> setRedisTemplate;

    @Autowired
    public SetRepository(final RebrickableAPIRepository rebrickableAPIGetter, final RedisTemplate<String, Set> setRedisTemplate) {
        this.rebrickableAPIGetter = rebrickableAPIGetter;
        this.setRedisTemplate = setRedisTemplate;
    }

    /// Random set from the database.
    ///
    /// @return random set
    /// @see Set
    public Set getRandomSet() {
        var set = this.rebrickableAPIGetter.randomSet();
        LOG.debug("Request: random set with {}", set);
        return set;
    }

    /// Random set from the database.
    ///
    /// @param theme lego set theme
    /// @return random lego set
    /// @see Theme
    /// @see Set
    public Set getRandomSet(Theme theme) {
        var set = this.rebrickableAPIGetter.randomSet(theme);
        LOG.debug("Request: random set with {} and theme {}", set, theme.getThemeName());
        return set;
    }

    /// All sets found by request.
    ///
    /// If the request is cached it will be returned from the cache, otherwise it will be cached.
    ///
    /// @param request search request
    /// @return set collection
    /// @see Set
    public Collection<Set> search(String request) {
        LOG.debug("New search request: {}", request);
        var redisKey = SetRedisTemplateConfig.SET_KEY_GENERATOR.apply(request);

        var sets = setRedisTemplate.opsForList().range(redisKey, 0, -1);

        if (sets == null || sets.isEmpty()) {
            sets = rebrickableAPIGetter.search(request);
            if (sets == null || sets.isEmpty()) return List.of();
            setRedisTemplate.opsForList().rightPushAll(redisKey, sets);
            setRedisTemplate.expire(redisKey, SetRedisTemplateConfig.TTL_DURATION);
            LOG.debug("Request {} was cached", request);
        }

        return sets;
    }

    /// All sets found by request.
    ///
    /// If the request is cached it will be returned from the cache, otherwise it will be cached.
    ///
    /// @param search       search request. Must not be null
    /// @param orderingType sort type. Can be null
    /// @return set list
    /// @see Set
    /// @see OrderingType
    public Collection<Set> search(String search, OrderingType orderingType) {

        if (orderingType == null)
            return search(search);

        LOG.debug("New search request: {} with {} order type", search, orderingType.getJsonProperty());

        var redisKey = SetRedisTemplateConfig.SET_KEY_GENERATOR.apply(String.format("%s:%s", search, orderingType));
        var sets = setRedisTemplate.opsForList().range(redisKey, 0, -1);

        if (sets == null || sets.isEmpty()) {
            sets = rebrickableAPIGetter.search(search, orderingType);
            if (sets == null || sets.isEmpty()) return List.of();
            setRedisTemplate.opsForList().rightPushAll(redisKey, sets);
            setRedisTemplate.expire(redisKey, SetRedisTemplateConfig.TTL_DURATION);
            LOG.debug("Search request {} was cached with key {}", search, redisKey);
        }

        return sets;
    }

}
