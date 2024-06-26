package org.lts.lego_teh_set.rebrickableAPI.returned_objects;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SetTest {

    private static final String json = "{\n" +
            "  \"set_num\": \"42111-1\",\n" +
            "  \"name\": \"Dom's Dodge Charger\",\n" +
            "  \"year\": 2020,\n" +
            "  \"theme_id\": 1,\n" +
            "  \"num_parts\": 1077,\n" +
            "  \"set_img_url\": \"https://cdn.rebrickable.com/media/sets/42111-1/56361.jpg\",\n" +
            "  \"set_url\": \"https://rebrickable.com/sets/42111-1/doms-dodge-charger/\",\n" +
            "  \"last_modified_dt\": \"2023-04-23T10:54:36.190875Z\"\n" +
            "}";
    private Set set;

    @Before
    public void set_init() {
        this.set = new Set("42111-1",
                "Dom's Dodge Charger",
                2020,
                1,
                1077,
                "https://cdn.rebrickable.com/media/sets/42111-1/56361.jpg",
                "https://rebrickable.com/sets/42111-1/doms-dodge-charger/",
                "2023-04-23T10:54:36.190875Z"
        );
    }

    @Test
    public void getSetNum() {
        assertEquals("42111-1" ,set.getSetNum());
    }

    @Test
    public void getName() {
        assertEquals("Dom's Dodge Charger", set.getName());
    }

    @Test
    public void getYear() {
        assertEquals(2020, set.getYear());
    }

    @Test
    public void getThemeId() {
        assertEquals(1, set.getThemeId());
    }

    @Test
    public void getNumParts() {
        assertEquals(1077, set.getNumParts());
    }

    @Test
    public void getSetImageUrl() {
        assertEquals("https://cdn.rebrickable.com/media/sets/42111-1/56361.jpg", set.getSetImageUrl());
    }

    @Test
    public void getSetUrl() {
        assertEquals("https://rebrickable.com/sets/42111-1/doms-dodge-charger/", set.getSetUrl());
    }

    @Test
    public void getLastModifiedDate() {
        assertEquals("2023-04-23T10:54:36.190875Z", set.getLastModifiedDate());
    }

    @Test
    public void setSetNum() {
        set.setSetNum("aboba");
        assertEquals("aboba", set.getSetNum());
    }

    @Test
    public void setName() {
        set.setName("ok");
        assertEquals("ok", set.getName());
    }

    @Test
    public void setYear() {
        set.setYear(2006);
        assertEquals(2006, set.getYear());
    }

    @Test
    public void setThemeId() {
        set.setThemeId(0);
        assertEquals(0, set.getThemeId());
    }

    @Test
    public void setNumParts() {
        set.setNumParts(-1);
        assertEquals(-1, set.getNumParts());
    }

    @Test
    public void setSetImageUrl() {
        set.setSetImageUrl("url");
        assertEquals("url", set.getSetImageUrl());
    }

    @Test
    public void setSetUrl() {
        set.setSetUrl("url");
        assertEquals("url", set.getSetUrl());
    }

    @Test
    public void setLastModifiedDate() {
        set.setLastModifiedDate("a");
        assertEquals("a", set.getLastModifiedDate());
    }

    @Test
    public void getSetFromJSONObject() {
        Set set = Set.getSetFromJSONObject(new JSONObject(json));

        assertEquals("42111-1", set.getSetNum());
        assertEquals("Dom's Dodge Charger", set.getName());
        assertEquals(2020, set.getYear());
        assertEquals(1, set.getThemeId());
        assertEquals(1077, set.getNumParts());
        assertEquals("https://cdn.rebrickable.com/media/sets/42111-1/56361.jpg", set.getSetImageUrl());
        assertEquals("https://rebrickable.com/sets/42111-1/doms-dodge-charger/", set.getSetUrl());
        assertEquals("2023-04-23T10:54:36.190875Z", set.getLastModifiedDate());

        set.setSetNum("1");
        set.setName("aboba");
        set.setYear(0);
        set.setThemeId(1);
        set.setNumParts(-1);
        set.setSetImageUrl("imageUrl");
        set.setSetUrl("serUrl");
        set.setLastModifiedDate("LastModifiedDate");

        assertEquals("1", set.getSetNum());
        assertEquals("aboba", set.getName());
        assertEquals(0, set.getYear());
        assertEquals(1, set.getThemeId());
        assertEquals(-1, set.getNumParts());
        assertEquals("imageUrl", set.getSetImageUrl());
        assertEquals("serUrl", set.getSetUrl());
        assertEquals("LastModifiedDate", set.getLastModifiedDate());
    }
}