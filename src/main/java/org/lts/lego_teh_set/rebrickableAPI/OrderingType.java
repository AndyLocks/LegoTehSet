package org.lts.lego_teh_set.rebrickableAPI;

import org.jetbrains.annotations.Contract;

/// Type of set sorting.
///
/// Required for requests to the [rebrickable API](https://rebrickable.com/api/v3/docs/)
public enum OrderingType {

    YEAR("year"),
    NAME("name"),
    NUM_PARTS("num_parts");

    private final String jsonProperty;

    OrderingType(String jsonProperty) {
        this.jsonProperty = jsonProperty;
    }

    public String getJsonProperty() {
        return this.jsonProperty;
    }

    /// Gives the type by name.
    ///
    /// @param jsonProperty property name
    /// @return sorting object, null if the argument is null
    /// @throws RuntimeException if the property name is not correct
    @Contract("null -> null")
    public static OrderingType fromJsonProperty(String jsonProperty) {
        return switch (jsonProperty) {
            case "year" -> YEAR;
            case "name" -> NAME;
            case "num_parts" -> NUM_PARTS;
            case null -> null;
            default -> throw new RuntimeException("Invalid value");
        };
    }

    @Override
    public String toString() {
        return "OrderingType{" +
                "jsonProperty='" + jsonProperty + '\'' +
                '}';
    }

}
