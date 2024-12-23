package org.lts.lego_teh_set.rebrickableAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.lts.lego_teh_set.rebrickableAPI.dto.builder.SetBuilder;

/// Represents a lego set.
public record Set(
        @JsonProperty("set_num")
        String number,

        @JsonProperty("name")
        String name,

        @JsonProperty("year")
        int year,

        @JsonProperty("theme_id")
        int themeId,

        @JsonProperty("num_parts")
        int parts,

        @JsonProperty("set_img_url")
        String setImageUrl,

        @JsonProperty("set_url")
        String setUrl,

        @JsonProperty("last_modified_dt")
        String lastModifiedDate
) {

    /// @return a builder, witch can be used to create a {@link Set}
    public static SetBuilder builder() {
        return new SetBuilder();
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
