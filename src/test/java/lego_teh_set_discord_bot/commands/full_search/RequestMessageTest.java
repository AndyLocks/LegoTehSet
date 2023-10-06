package lego_teh_set_discord_bot.commands.full_search;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import rebrickableAPI.returned_objects.Set;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RequestMessageTest {
    private static final List<Set> setList = new ArrayList<>();
    private RequestMessage requestMessage;

    @BeforeClass
    public static void set_list_init() {
        setList.add(
                new Set("42111-1",
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
                new Set("9398-1",
                        "4 x 4 Crawler",
                        2012,
                        1,
                        1327,
                        "https://cdn.rebrickable.com/media/sets/9398-1/47262.jpg",
                        "https://rebrickable.com/sets/9398-1/4-x-4-crawler/",
                        "2019-12-18T08:47:09.848245Z"
                )
        );
        setList.add(
                new Set("89345",
                        "aboba",
                        2024,
                        1,
                        2942,
                        "https://cdn.rebrickable.com/media/sets/9398-1/47262.jpg",
                        "https://rebrickable.com/sets/9398-1/4-x-4-crawler/",
                        "2019-12-18T08:47:09.848245Z"
                )
        );
        setList.add(
                new Set("89345",
                        "aboba",
                        2024,
                        1,
                        2942,
                        "https://cdn.rebrickable.com/media/sets/9398-1/47262.jpg",
                        "https://rebrickable.com/sets/9398-1/4-x-4-crawler/",
                        "2019-12-18T08:47:09.848245Z"
                )
        );
        setList.add(new Set());
        setList.add(new Set());
        setList.add(new Set());
        setList.add(new Set());
        setList.add(new Set());
        setList.add(new Set());
        setList.add(new Set());
        setList.add(new Set());
        setList.add(new Set());
        setList.add(new Set());
        setList.add(new Set());
        setList.add(new Set());
        setList.add(new Set());
        setList.add(new Set());
        setList.add(new Set());
        setList.add(new Set());
        setList.add(
                new Set("42124-1",
                        "Off-Road Buggy",
                        2021,
                        1,
                        374,
                        "https://cdn.rebrickable.com/media/sets/42124-1/76826.jpg",
                        "https://rebrickable.com/sets/42124-1/off-road-buggy/",
                        "2020-12-04T19:34:44.127031Z"
                )
        );
    }

    @Before
    public void request_message_init() {
        this.requestMessage = new RequestMessage(setList);
    }

    @Test
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

        assertEquals(set1, this.requestMessage.getCurrentSet());
        this.requestMessage.next();
        assertEquals(set2, this.requestMessage.getCurrentSet());
        this.requestMessage.prev();
        assertEquals(set1, this.requestMessage.getCurrentSet());

        this.requestMessage.next();
        this.requestMessage.next();
        this.requestMessage.setCurrentIndex(0);
        assertEquals(set1, this.requestMessage.getCurrentSet());

        this.requestMessage.getLastEmbedBuilder();
        assertEquals(lastSet, this.requestMessage.getCurrentSet());

        this.requestMessage.getFirstEmbedBuilder();
        assertEquals(set1, this.requestMessage.getCurrentSet());
    }

    @Test
    public void size_test() {
        assertEquals(21, this.requestMessage.size());
    }

    @Test
    public void checking_next_and_previous_set() {
        assertTrue(this.requestMessage.hasNext());
        assertFalse(this.requestMessage.hasPrev());
        this.requestMessage.next();
        assertTrue(this.requestMessage.hasNext());
        assertTrue(this.requestMessage.hasPrev());
    }

    @Test
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
            this.requestMessage.next();
        }
        assertEquals(set1, this.requestMessage.getCurrentSet());
    }

    @Test
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
            this.requestMessage.prev();
        }
        assertEquals(set1, this.requestMessage.getCurrentSet());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void set_current_index_minus_one() {
        this.requestMessage.setCurrentIndex(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void set_current_index_1000() {
        this.requestMessage.setCurrentIndex(1000);
    }
}