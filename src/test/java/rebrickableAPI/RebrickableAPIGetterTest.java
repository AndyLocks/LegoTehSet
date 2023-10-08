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
    public void getPage() {
        List<Set> setList = new ArrayList<>();
        setList.add(
                new Set(
                        "002-1",
                        "4.5V Samsonite Gears Motor Set",
                        1965,
                        1,
                        3,
                        "https://cdn.rebrickable.com/media/sets/002-1/3643.jpg",
                        "https://rebrickable.com/sets/002-1/45v-samsonite-gears-motor-set/",
                        "2018-05-06T11:34:38.029101Z"
                )
        );
        setList.add(
                new Set(
                        "002253963-1",
                        "Legend of Chima: Corbeaux et Gorilles",
                        2013,
                        497,
                        4,
                        "https://cdn.rebrickable.com/media/sets/002253963-1/100555.jpg",
                        "https://rebrickable.com/sets/002253963-1/legend-of-chima-corbeaux-et-gorilles/",
                        "2022-04-08T04:43:50.619168Z"
                )
        );
        assertArrayEquals(setList.toArray(), this.rebrickableAPIGetter.getPage(5, 2).toArray());
    }

    @Test
    public void getPageWithFiveSets() {
        List<Set> setList = new ArrayList<>();
        setList.add(
                new Set(
                        "001-1",
                        "Gears",
                        1965,
                        1,
                        43,
                        "https://cdn.rebrickable.com/media/sets/001-1/11530.jpg",
                        "https://rebrickable.com/sets/001-1/gears/",
                        "2018-05-05T20:39:47.277922Z"
                )
        );
        setList.add(
                new Set(
                        "0011-2",
                        "Town Mini-Figures",
                        1979,
                        67,
                        12,
                        "https://cdn.rebrickable.com/media/sets/0011-2/3318.jpg",
                        "https://rebrickable.com/sets/0011-2/town-mini-figures/",
                        "2021-07-04T19:03:52.273186Z"
                )
        );
        setList.add(
                new Set(
                        "0011-3",
                        "Castle 2 for 1 Bonus Offer",
                        1987,
                        199,
                        0,
                        "https://cdn.rebrickable.com/media/sets/0011-3/2729.jpg",
                        "https://rebrickable.com/sets/0011-3/castle-2-for-1-bonus-offer/",
                        "2012-04-01T04:47:31.488559Z"
                )
        );
        setList.add(
                new Set(
                        "0012-1",
                        "Space Mini-Figures",
                        1979,
                        143,
                        12,
                        "https://cdn.rebrickable.com/media/sets/0012-1/1831.jpg",
                        "https://rebrickable.com/sets/0012-1/space-mini-figures/",
                        "2013-12-12T23:12:14.245364Z"
                )
        );
        setList.add(
                new Set(
                        "0013-1",
                        "Space Mini-Figures",
                        1979,
                        143,
                        12,
                        "https://cdn.rebrickable.com/media/sets/0013-1/1988.jpg",
                        "https://rebrickable.com/sets/0013-1/space-mini-figures/",
                        "2013-11-08T20:55:48.506908Z"
                )
        );
        assertArrayEquals(setList.toArray(), this.rebrickableAPIGetter.getPageWithFiveSets(1).toArray());
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