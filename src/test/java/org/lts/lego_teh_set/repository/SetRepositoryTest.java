package org.lts.lego_teh_set.repository;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.lts.lego_teh_set.cache.RequestAndOrderingTypeToKeyConverter;
import org.lts.lego_teh_set.cache.SetCacheRepository;
import org.lts.lego_teh_set.rebrickableAPI.OrderingType;
import org.lts.lego_teh_set.rebrickableAPI.RebrickableAPIGetter;
import org.lts.lego_teh_set.rebrickableAPI.returned_objects.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SetRepositoryTest {

    private String request = "car";
    private OrderingType orderingType = OrderingType.NAME;
    private RebrickableAPIGetter rebrickableAPIGetter =
            RebrickableAPIGetter.getInstance();

    @Autowired
    private SetCacheRepository setCacheRepository;

    @Autowired
    private SetRepository setRepository;

    @Test
    void getSearchResult() {

        List<Set> setsFromRepository = setRepository.getSearchResult(request);
        List<Set> setsFromCache = setCacheRepository.load(request);

        assertArrayEquals(setsFromRepository.toArray(), setsFromCache.toArray());

        List<Set> setsFromRepository1 = setRepository.getSearchResult(request);
        List<Set> setsFromCache1 = setCacheRepository.load(request);

        assertArrayEquals(setsFromRepository1.toArray(), setsFromCache1.toArray());
    }

    @Test
    void testGetSearchResultWithOrderingType() {

        var key = RequestAndOrderingTypeToKeyConverter.withDefaults().convert(request, orderingType);

        List<Set> setsFromRepository = setRepository.getSearchResult(request, orderingType);
        List<Set> setsFromCache = setCacheRepository.load(key);

        assertArrayEquals(setsFromRepository.toArray(), setsFromCache.toArray());

        List<Set> setsFromRepository1 = setRepository.getSearchResult(request, orderingType);
        List<Set> setsFromCache1 = setCacheRepository.load(key);

        assertArrayEquals(setsFromRepository1.toArray(), setsFromCache1.toArray());

    }

    @After
    public void clearCache() {

        setCacheRepository.delete(request);
        setCacheRepository.delete(RequestAndOrderingTypeToKeyConverter.withDefaults()
                .convert(request, orderingType));
    }

}