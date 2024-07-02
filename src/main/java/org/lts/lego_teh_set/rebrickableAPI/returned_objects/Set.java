package org.lts.lego_teh_set.rebrickableAPI.returned_objects;

import org.json.JSONObject;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a lego set.
 * Is a data container.
 */
@RedisHash(value = "set")
public class Set implements Serializable {

    private String setNum;
    private String name;
    private int year;
    private int themeId;
    private int numParts;
    private String setImageUrl;
    private String setUrl;
    private String lastModifiedDate;

    public Set() {
    }

    public Set(String setNum, String name,
               int year, int themeId,
               int numParts,
               String setImageUrl,
               String setUrl,
               String lastModifiedDate) {
        this.setNum = setNum;
        this.name = name;
        this.year = year;
        this.themeId = themeId;
        this.numParts = numParts;
        this.setImageUrl = setImageUrl;
        this.setUrl = setUrl;
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getSetNum() {
        return setNum;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getThemeId() {
        return themeId;
    }

    public int getNumParts() {
        return numParts;
    }

    public String getSetImageUrl() {
        return setImageUrl;
    }

    public String getSetUrl() {
        return setUrl;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setSetNum(String setNum) {
        this.setNum = setNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public void setNumParts(int numParts) {
        this.numParts = numParts;
    }

    public void setSetImageUrl(String setImageUrl) {
        this.setImageUrl = setImageUrl;
    }

    public void setSetUrl(String setUrl) {
        this.setUrl = setUrl;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Set set = (Set) o;
        return year == set.year && themeId == set.themeId && numParts == set.numParts
                && Objects.equals(setNum, set.setNum) && Objects.equals(name, set.name)
                && Objects.equals(setImageUrl, set.setImageUrl) && Objects.equals(setUrl, set.setUrl)
                && Objects.equals(lastModifiedDate, set.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(setNum, name, year, themeId, numParts, setImageUrl, setUrl, lastModifiedDate);
    }

    /**
     * Gives a set from a json object.
     *
     * @param jsonObject object with parameters set_num, name, year, theme_id,
     *                   num_parts, set_img_url, set_url, last_modified_dt.
     *                   example last_modified_dt: 2023-04-23T10:54:36.190875Z
     * @return возвращает уже заполненный объект набора
     */
    public static Set getSetFromJSONObject(JSONObject jsonObject) {
        Set set = new Set();

        set.setSetNum(jsonObject.getString("set_num"));
        set.setName(jsonObject.getString("name"));
        set.setYear(jsonObject.getInt("year"));
        set.setThemeId(jsonObject.getInt("theme_id"));
        set.setNumParts(jsonObject.getInt("num_parts"));
        set.setSetImageUrl(jsonObject.getString("set_img_url"));
        set.setSetUrl(jsonObject.getString("set_url"));
        set.setLastModifiedDate(jsonObject.getString("last_modified_dt"));

        return set;
    }

    @Override
    public String toString() {
        return "Set{" +
                "setNum='" + setNum + '\'' +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", themeId=" + themeId +
                ", numParts=" + numParts +
                ", setImageUrl='" + setImageUrl + '\'' +
                ", setUrl='" + setUrl + '\'' +
                ", lastModifiedDate='" + lastModifiedDate + '\'' +
                '}';
    }

}
