package org.lts.profile_hub.entity.dto;

import org.lts.Api;
import org.lts.profile_hub.entity.dto.builder.SetBuilder;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;

public record SetDto(
        UUID id,
        String number,
        String name,
        int year,
        int themeId,
        int parts,
        String setImageUrl,
        String setUrl,
        String lastModifiedDate
) {

    /// @throws IllegalArgumentException if set is null or contains at least one null field
    public static SetDto from(Api.Set set) {
        if (set == null)
            throw new IllegalArgumentException("Set cannot be null");

        Assert.notNull(set.getName(), "Name cannot be null");
        Assert.notNull(set.getSetImageUrl(), "Image cannot be null");
        Assert.notNull(set.getSetUrl(), "Url cannot be null");
        Assert.notNull(set.getLastModifiedDate(), "Last modified date cannot be null");

        return new SetDto(null,
                set.getNumber(),
                set.getName(),
                set.getYear(),
                set.getThemeId(),
                set.getParts(),
                set.getSetImageUrl(),
                set.getSetUrl(),
                set.getLastModifiedDate()
        );
    }

    /// @throws IllegalArgumentException if set contains at least one null field
    public static SetDto from(org.lts.profile_hub.entity.jpa.Set set) {
        Assert.notNull(set.getName(), "Name cannot be null");
        Assert.notNull(set.getSetImageUrl(), "Image cannot be null");
        Assert.notNull(set.getSetUrl(), "Url cannot be null");
        Assert.notNull(set.getLastModifiedDate(), "Last modified date cannot be null");

        return new SetDto(set.getId(),
                set.getNumber(),
                set.getName(),
                set.getYear(),
                set.getThemeId(),
                set.getParts(),
                set.getSetImageUrl(),
                set.getSetUrl(),
                set.getLastModifiedDate()
        );
    }

    public static org.lts.profile_hub.entity.jpa.Set toJpa(SetDto set) {
        return new org.lts.profile_hub.entity.jpa.Set(
                set.id(),
                set.number(),
                set.name(),
                set.year(),
                set.themeId(),
                set.parts(),
                set.setImageUrl(),
                set.setUrl(),
                set.lastModifiedDate(),
                List.of());
    }

    /// @return a builder, witch can be used to create a {@link SetDto}
    public static SetBuilder builder() {
        return new SetBuilder();
    }

    public org.lts.profile_hub.entity.jpa.Set toJpa() {
        return toJpa(this);
    }

    @Override
    public String toString() {
        return "Set{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", themeId=" + themeId +
                ", parts=" + parts +
                ", setImageUrl='" + setImageUrl + '\'' +
                ", setUrl='" + setUrl + '\'' +
                ", lastModifiedDate='" + lastModifiedDate + '\'' +
                '}';
    }

}
