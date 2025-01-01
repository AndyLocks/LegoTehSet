package org.lts.profile_hub.entity.jpa;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Table
@Entity
public class Set implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int year;

    @Column(name = "theme_id", nullable = false)
    private int themeId;

    @Column(nullable = false)
    private int parts;

    @Column(name = "set_image_url", nullable = false)
    private String setImageUrl;

    @Column(name = "set_url", nullable = false)
    private String setUrl;

    @Column(name = "last_modified_date", nullable = false)
    private String lastModifiedDate;

    @ManyToMany(mappedBy = "favouriteSets", fetch = FetchType.LAZY)
    private List<DiscordUser> users = List.of();

    public Set() {}

    public Set(UUID id, String number, String name, int year, int themeId, int parts, String setImageUrl, String setUrl, String lastModifiedDate, List<DiscordUser> users) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.year = year;
        this.themeId = themeId;
        this.parts = parts;
        this.setImageUrl = setImageUrl;
        this.setUrl = setUrl;
        this.lastModifiedDate = lastModifiedDate;
        this.users = users;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public int getParts() {
        return parts;
    }

    public void setParts(int parts) {
        this.parts = parts;
    }

    public String getSetImageUrl() {
        return setImageUrl;
    }

    public void setSetImageUrl(String setImageUrl) {
        this.setImageUrl = setImageUrl;
    }

    public String getSetUrl() {
        return setUrl;
    }

    public void setSetUrl(String setUrl) {
        this.setUrl = setUrl;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public List<DiscordUser> getUsers() {
        return users;
    }

    public void setUsers(List<DiscordUser> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Set{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", themeId=" + themeId +
                ", parts=" + parts +
                ", setImageUrl='" + setImageUrl + '\'' +
                ", setUrl='" + setUrl + '\'' +
                ", lastModifiedDate='" + lastModifiedDate + '\'' +
                ", users=" + users +
                '}';
    }

}
