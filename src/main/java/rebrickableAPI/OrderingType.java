package rebrickableAPI;

/**
 * Type of set sorting.
 * <p>
 * Required for requests to the <a href="https://rebrickable.com/api/v3/docs/">rebrickable API</a>.
 */
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

    /**
     * Gives the type by name.
     *
     * @param jsonProperty property name
     * @throws RuntimeException if the property name is not correct
     * @return sorting object
     */
    public static OrderingType getOrderingTypeFromString(String jsonProperty) {
        return switch (jsonProperty) {
            case "year" -> YEAR;
            case "name" -> NAME;
            case "num_parts" -> NUM_PARTS;
            default -> throw new RuntimeException("Invalid value");
        };
    }
}
