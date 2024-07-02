package org.lts.lego_teh_set.rebrickableAPI.cache;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.lts.lego_teh_set.cache.SetCacheRepository;
import org.lts.lego_teh_set.rebrickableAPI.RebrickableAPIGetter;
import org.lts.lego_teh_set.rebrickableAPI.returned_objects.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SetCacheRepositoryTest {

    @Autowired
    private SetCacheRepository repository;

    private RebrickableAPIGetter api = RebrickableAPIGetter.getInstance();

    private final String request = "dodge";

    @Test
    public void save() {
        List<Set> sets = api.getSearchResult(request);

        repository.write(request, sets);

        List<Set> returnedSets = repository.load(request);

        assertArrayEquals(sets.toArray(), returnedSets.toArray());
    }

    @Test
    public void get_null_set() {
        List<Set> set = repository.load("KjdbOkdbelqkBldk");

        assertNull(set);
    }

    @After
    public void clear_cache() {
        repository.delete(request);

        assertTrue(true);
    }
}