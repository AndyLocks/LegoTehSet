package rebrickableAPI;

import org.junit.Before;
import org.junit.Test;
import rebrickableAPI.returned_objects.Set;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RebrickableAPIGetterTest {

    private RebrickableAPIGetter rebrickableAPIGetter;

    @Before
    public void obj_init() {
        this.rebrickableAPIGetter = new RebrickableAPIGetter();
    }

    @Test
    public void getSearchResult() {
        List<Set> setList = new ArrayList<>();
        setList.add(
                new Set(
                        "42111-1",
                        "Dom's Dodge Charger",
                        2020,
                        1,
                        1077,
                        "https://cdn.rebrickable.com/media/sets/42111-1/56361.jpg",
                        "https://rebrickable.com/sets/42111-1/doms-dodge-charger/",
                        "2023-04-23T10:54:36.190875Z"
                )
        );
        setList.add(
                new Set(
                        "75893-1",
                        "2018 Dodge Challenger SRT Demon and 1970 Dodge Charger R/T",
                        2019,
                        601,
                        485,
                        "https://cdn.rebrickable.com/media/sets/75893-1/15770.jpg",
                        "https://rebrickable.com/sets/75893-1/2018-dodge-challenger-srt-demon-and-1970-dodge-charger-rt/",
                        "2019-01-20T15:42:38.388881Z"
                )
        );
        setList.add(
                new Set(
                        "76904-1",
                        "Mopar Dodge//SRT Top Fuel Dragster and 1970 Dodge Challenger T/A",
                        2021,
                        601,
                        627,
                        "https://cdn.rebrickable.com/media/sets/76904-1/88304.jpg",
                        "https://rebrickable.com/sets/76904-1/mopar-dodgesrt-top-fuel-dragster-and-1970-dodge-challenger-ta/",
                        "2021-05-18T04:22:54.460739Z"
                )
        );
        setList.add(
                new Set(
                        "76912-1",
                        "Fast & Furious 1970 Dodge Charger R/T",
                        2022,
                        601,
                        345,
                        "https://cdn.rebrickable.com/media/sets/76912-1/104445.jpg",
                        "https://rebrickable.com/sets/76912-1/fast-furious-1970-dodge-charger-rt/",
                        "2022-06-15T08:14:09.778281Z"
                )
        );
        assertArrayEquals(setList.toArray(), this.rebrickableAPIGetter.getSearchResult("dodge").toArray());
    }

    @Test
    public void test_ordering_type_year() {
        List<Set> setList = new ArrayList<>();
        setList.add(
                new Set(
                        "75893-1",
                        "2018 Dodge Challenger SRT Demon and 1970 Dodge Charger R/T",
                        2019,
                        601,
                        485,
                        "https://cdn.rebrickable.com/media/sets/75893-1/15770.jpg",
                        "https://rebrickable.com/sets/75893-1/2018-dodge-challenger-srt-demon-and-1970-dodge-charger-rt/",
                        "2019-01-20T15:42:38.388881Z"
                )
        );
        setList.add(
                new Set(
                        "42111-1",
                        "Dom's Dodge Charger",
                        2020,
                        1,
                        1077,
                        "https://cdn.rebrickable.com/media/sets/42111-1/56361.jpg",
                        "https://rebrickable.com/sets/42111-1/doms-dodge-charger/",
                        "2023-04-23T10:54:36.190875Z"
                )
        );
        setList.add(
                new Set(
                        "76904-1",
                        "Mopar Dodge//SRT Top Fuel Dragster and 1970 Dodge Challenger T/A",
                        2021,
                        601,
                        627,
                        "https://cdn.rebrickable.com/media/sets/76904-1/88304.jpg",
                        "https://rebrickable.com/sets/76904-1/mopar-dodgesrt-top-fuel-dragster-and-1970-dodge-challenger-ta/",
                        "2021-05-18T04:22:54.460739Z"
                )
        );
        setList.add(
                new Set(
                        "76912-1",
                        "Fast & Furious 1970 Dodge Charger R/T",
                        2022,
                        601,
                        345,
                        "https://cdn.rebrickable.com/media/sets/76912-1/104445.jpg",
                        "https://rebrickable.com/sets/76912-1/fast-furious-1970-dodge-charger-rt/",
                        "2022-06-15T08:14:09.778281Z"
                )
        );
        assertArrayEquals(setList.toArray(), this.rebrickableAPIGetter.getSearchResult("dodge", OrderingType.YEAR).toArray());
    }

    @Test
    public void test_ordering_type_name() {
        List<Set> setList = new ArrayList<>();
        setList.add(
                new Set(
                        "75893-1",
                        "2018 Dodge Challenger SRT Demon and 1970 Dodge Charger R/T",
                        2019,
                        601,
                        485,
                        "https://cdn.rebrickable.com/media/sets/75893-1/15770.jpg",
                        "https://rebrickable.com/sets/75893-1/2018-dodge-challenger-srt-demon-and-1970-dodge-charger-rt/",
                        "2019-01-20T15:42:38.388881Z"
                )
        );
        setList.add(
                new Set(
                        "42111-1",
                        "Dom's Dodge Charger",
                        2020,
                        1,
                        1077,
                        "https://cdn.rebrickable.com/media/sets/42111-1/56361.jpg",
                        "https://rebrickable.com/sets/42111-1/doms-dodge-charger/",
                        "2023-04-23T10:54:36.190875Z"
                )
        );
        setList.add(
                new Set(
                        "76912-1",
                        "Fast & Furious 1970 Dodge Charger R/T",
                        2022,
                        601,
                        345,
                        "https://cdn.rebrickable.com/media/sets/76912-1/104445.jpg",
                        "https://rebrickable.com/sets/76912-1/fast-furious-1970-dodge-charger-rt/",
                        "2022-06-15T08:14:09.778281Z"
                )
        );
        setList.add(
                new Set(
                        "76904-1",
                        "Mopar Dodge//SRT Top Fuel Dragster and 1970 Dodge Challenger T/A",
                        2021,
                        601,
                        627,
                        "https://cdn.rebrickable.com/media/sets/76904-1/88304.jpg",
                        "https://rebrickable.com/sets/76904-1/mopar-dodgesrt-top-fuel-dragster-and-1970-dodge-challenger-ta/",
                        "2021-05-18T04:22:54.460739Z"
                )
        );
        assertArrayEquals(setList.toArray(), this.rebrickableAPIGetter.getSearchResult("dodge", OrderingType.NAME).toArray());
    }

    @Test
    public void test_ordering_type_name_num_parts() {
        List<Set> setList = new ArrayList<>();
        setList.add(
                new Set(
                        "76912-1",
                        "Fast & Furious 1970 Dodge Charger R/T",
                        2022,
                        601,
                        345,
                        "https://cdn.rebrickable.com/media/sets/76912-1/104445.jpg",
                        "https://rebrickable.com/sets/76912-1/fast-furious-1970-dodge-charger-rt/",
                        "2022-06-15T08:14:09.778281Z"
                )
        );
        setList.add(
                new Set(
                        "75893-1",
                        "2018 Dodge Challenger SRT Demon and 1970 Dodge Charger R/T",
                        2019,
                        601,
                        485,
                        "https://cdn.rebrickable.com/media/sets/75893-1/15770.jpg",
                        "https://rebrickable.com/sets/75893-1/2018-dodge-challenger-srt-demon-and-1970-dodge-charger-rt/",
                        "2019-01-20T15:42:38.388881Z"
                )
        );
        setList.add(
                new Set(
                        "76904-1",
                        "Mopar Dodge//SRT Top Fuel Dragster and 1970 Dodge Challenger T/A",
                        2021,
                        601,
                        627,
                        "https://cdn.rebrickable.com/media/sets/76904-1/88304.jpg",
                        "https://rebrickable.com/sets/76904-1/mopar-dodgesrt-top-fuel-dragster-and-1970-dodge-challenger-ta/",
                        "2021-05-18T04:22:54.460739Z"
                )
        );
        setList.add(
                new Set(
                        "42111-1",
                        "Dom's Dodge Charger",
                        2020,
                        1,
                        1077,
                        "https://cdn.rebrickable.com/media/sets/42111-1/56361.jpg",
                        "https://rebrickable.com/sets/42111-1/doms-dodge-charger/",
                        "2023-04-23T10:54:36.190875Z"
                )
        );
        assertArrayEquals(setList.toArray(), this.rebrickableAPIGetter.getSearchResult("dodge", OrderingType.NUM_PARTS).toArray());
    }

    @Test
    public void random_set_test() {
        Set set = this.rebrickableAPIGetter.getRundomSet();

        System.out.println(set.getSetNum());
        System.out.println(set.getName());
        System.out.println(set.getThemeId());
        System.out.println(set.getYear());
        System.out.println(set.getSetUrl());
        System.out.println(set.getSetImageUrl());
        System.out.println(set.getLastModifiedDate());
        System.out.println(set.getNumParts());
    }

    @Test
    public void random_set_with_theme_test() {
        Set set = this.rebrickableAPIGetter.getRundomSet(Theme.TECHNIC);
        System.out.println(set.getSetNum());
        System.out.println(set.getName());
        System.out.println(set.getThemeId());
        System.out.println(set.getYear());
        System.out.println(set.getSetUrl());
        System.out.println(set.getSetImageUrl());
        System.out.println(set.getLastModifiedDate());
        System.out.println(set.getNumParts());
        assertEquals(set.getThemeId(), 1);

        Set set2 = this.rebrickableAPIGetter.getRundomSet(Theme.CITY);
        System.out.println(set2.getSetNum());
        System.out.println(set2.getName());
        System.out.println(set2.getThemeId());
        System.out.println(set2.getYear());
        System.out.println(set2.getSetUrl());
        System.out.println(set2.getSetImageUrl());
        System.out.println(set2.getLastModifiedDate());
        System.out.println(set2.getNumParts());
        assertEquals(set2.getThemeId(), 52);
    }
}