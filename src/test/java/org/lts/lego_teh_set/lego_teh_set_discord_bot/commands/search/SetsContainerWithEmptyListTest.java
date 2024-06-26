package org.lts.lego_teh_set.lego_teh_set_discord_bot.commands.search;

import org.junit.Before;
import org.junit.Test;
import org.lts.lego_teh_set.command.search.EmptySetsContainerException;
import org.lts.lego_teh_set.command.search.SetsContainer;
import org.lts.lego_teh_set.rebrickableAPI.returned_objects.Set;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


public class SetsContainerWithEmptyListTest {
    private static final List<Set> setList = new ArrayList<>();
    private SetsContainer setsContainer;

    @Before
    public void setSetsContainer_init() {
        this.setsContainer = new SetsContainer(setList);
    }

    @Test(expected = EmptySetsContainerException.class)
    public void comparison_of_sets() {
        Set set1 = new Set("42111-1",
                "Dom's Dodge Charger",
                2020,
                1,
                1077,
                "https://cdn.rebrickable.com/media/sets/42111-1/56361.jpg",
                "https://rebrickable.com/sets/42111-1/doms-dodge-charger/",
                "2023-04-23T10:54:36.190875Z"
        );
        Set set2 = new Set("9398-1",
                "4 x 4 Crawler",
                2012,
                1,
                1327,
                "https://cdn.rebrickable.com/media/sets/9398-1/47262.jpg",
                "https://rebrickable.com/sets/9398-1/4-x-4-crawler/",
                "2019-12-18T08:47:09.848245Z"
        );
        Set lastSet = new Set("42124-1",
                "Off-Road Buggy",
                2021,
                1,
                374,
                "https://cdn.rebrickable.com/media/sets/42124-1/76826.jpg",
                "https://rebrickable.com/sets/42124-1/off-road-buggy/",
                "2020-12-04T19:34:44.127031Z"
        );

        assertEquals(set1, this.setsContainer.getCurrentSet());
        this.setsContainer.next();
        assertEquals(set2, this.setsContainer.getCurrentSet());
        this.setsContainer.prev();
        assertEquals(set1, this.setsContainer.getCurrentSet());

        this.setsContainer.next();
        this.setsContainer.next();
        this.setsContainer.setCurrentIndex(0);
        assertEquals(set1, this.setsContainer.getCurrentSet());

        this.setsContainer.toEnd();
        assertEquals(lastSet, this.setsContainer.getCurrentSet());

        this.setsContainer.toStart();
        assertEquals(set1, this.setsContainer.getCurrentSet());
    }

    @Test
    public void size_test() {
        assertEquals(0, this.setsContainer.size());
    }

    @Test
    public void checking_next_and_previous_set() {
        assertFalse(this.setsContainer.hasNext());
        assertFalse(this.setsContainer.hasPrev());
        this.setsContainer.next();
        assertFalse(this.setsContainer.hasNext());
        assertFalse(this.setsContainer.hasPrev());
    }

    @Test(expected = EmptySetsContainerException.class)
    public void next_1000() {
        Set set1 = new Set("42124-1",
                "Off-Road Buggy",
                2021,
                1,
                374,
                "https://cdn.rebrickable.com/media/sets/42124-1/76826.jpg",
                "https://rebrickable.com/sets/42124-1/off-road-buggy/",
                "2020-12-04T19:34:44.127031Z"
        );
        for (int i = 0; i < 1000; i++) {
            this.setsContainer.next();
        }
        assertEquals(set1, this.setsContainer.getCurrentSet());
    }

    @Test(expected = EmptySetsContainerException.class)
    public void prev_1000() {
        Set set1 = new Set("42111-1",
                "Dom's Dodge Charger",
                2020,
                1,
                1077,
                "https://cdn.rebrickable.com/media/sets/42111-1/56361.jpg",
                "https://rebrickable.com/sets/42111-1/doms-dodge-charger/",
                "2023-04-23T10:54:36.190875Z"
        );
        for (int i = 0; i < 1000; i++) {
            this.setsContainer.prev();
        }
        assertEquals(set1, this.setsContainer.getCurrentSet());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void set_current_index_minus_one() {
        this.setsContainer.setCurrentIndex(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void set_current_index_1000() {
        this.setsContainer.setCurrentIndex(1000);
    }
}