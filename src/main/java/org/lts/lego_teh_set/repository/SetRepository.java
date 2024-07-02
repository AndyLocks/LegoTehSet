package org.lts.lego_teh_set.repository;

import org.lts.lego_teh_set.ApplicationContextProvider;
import org.lts.lego_teh_set.cache.RequestAndOrderingTypeToKeyConverter;
import org.lts.lego_teh_set.cache.SetCacheRepository;
import org.lts.lego_teh_set.rebrickableAPI.OrderingType;
import org.lts.lego_teh_set.rebrickableAPI.RebrickableAPIGetter;
import org.lts.lego_teh_set.rebrickableAPI.Theme;
import org.lts.lego_teh_set.rebrickableAPI.returned_objects.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Combines {@link org.lts.lego_teh_set.rebrickableAPI.RebrickableAPIGetter} and {@link org.lts.lego_teh_set.cache.SetCacheRepository}
 * <p>
 * <p>
 * If the request is cached it will be returned from the cache,
 * otherwise a new request will be made and cached.
 */
@Repository
public class SetRepository {

    private final Logger LOG = LoggerFactory.getLogger(SetRepository.class);
    private RebrickableAPIGetter rebrickableAPIGetter = RebrickableAPIGetter.getInstance();
    private SetCacheRepository cacheRepository = ApplicationContextProvider.getApplicationContext()
            .getBean("setCacheRepository", SetCacheRepository.class);

    /**
     * Random set from the database.
     * <p>
     * This method copies {@link RebrickableAPIGetter#getRandomSet()}
     * and it doesn't cache the request
     *
     * @return random set
     * @see Set
     */
    public Set getRandomSet() {

        var set = this.rebrickableAPIGetter.getRandomSet();
        LOG.info("Request: random set with {}", set);
        return set;
    }

    /**
     * Random set from the database.
     * <p>
     * This method copies {@link RebrickableAPIGetter#getRandomSet(Theme)}
     * and it doesn't cache the request
     *
     * @param theme lego set theme
     * @return random lego set
     * @see Theme
     * @see Set
     */
    public Set getRandomSet(Theme theme) {

        var set = this.rebrickableAPIGetter.getRandomSet(theme);
        LOG.info("Request: random set with {} and theme {}", set, theme.getThemeName());
        return set;
    }

    /**
     * All sets found by request.
     * <p>
     * If the request is cached it will be returned
     * from the cache, otherwise it will be cached.
     *
     * @param search search request
     * @return set list
     * @see Set
     */
    public List<Set> getSearchResult(String search) {

        LOG.info("New search request: {}", search);

        List<Set> sets = this.cacheRepository.load(search);

        if (sets == null) {
            sets = this.rebrickableAPIGetter.getSearchResult(search);
            this.cacheRepository.write(search, sets);
            LOG.info("Request {} was cached", search);
        }

        return sets;
    }

    /**
     * All sets found by request.
     * <p>
     * If the request is cached it will be returned
     * from the cache, otherwise it will be cached.
     *
     * @param search       search request
     * @param orderingType sort type
     * @return set list
     * @see Set
     * @see OrderingType
     */
    public List<Set> getSearchResult(String search, OrderingType orderingType) {

        LOG.info("New search request: {} with {} order type", search, orderingType.getJsonProperty());

        var key = RequestAndOrderingTypeToKeyConverter.withDefaults().convert(search, orderingType);
        List<Set> sets = this.cacheRepository.load(key);

        if (sets == null) {
            sets = this.rebrickableAPIGetter.getSearchResult(search, orderingType);
            this.cacheRepository.write(key, sets);
            LOG.info("Search request {} was cached with key {}", search, key);
        }

        return sets;
    }

}
