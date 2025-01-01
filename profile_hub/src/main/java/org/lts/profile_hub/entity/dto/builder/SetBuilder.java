package org.lts.profile_hub.entity.dto.builder;

import org.lts.profile_hub.entity.dto.SetDto;

import java.util.UUID;

/// A builder, witch can be used to create a {@link SetDto}
public class SetBuilder {

    private UUID id = null;
    private String setNum;
    private String name;
    private int year;
    private int themeId;
    private int numParts;
    private String setImageUrl;
    private String setUrl;
    private String lastModifiedDate;

    public static SetBuilder builder() {
        return new SetBuilder();
    }


    public SetBuilder lastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public SetBuilder setUrl(String setUrl) {
        this.setUrl = setUrl;
        return this;
    }

    public SetBuilder setImageUrl(String setImageUrl) {
        this.setImageUrl = setImageUrl;
        return this;
    }

    public SetBuilder parts(int numParts) {
        this.numParts = numParts;
        return this;
    }

    public SetBuilder themeId(int themeId) {
        this.themeId = themeId;
        return this;
    }

    public SetBuilder year(int year) {
        this.year = year;
        return this;
    }

    public SetBuilder name(String name) {
        this.name = name;
        return this;
    }

    public SetBuilder number(String setNum) {
        this.setNum = setNum;
        return this;
    }

    public SetBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public SetDto build() {
        return new SetDto(id, setNum, name, year, themeId, numParts, setImageUrl, setUrl, lastModifiedDate);
    }

}
