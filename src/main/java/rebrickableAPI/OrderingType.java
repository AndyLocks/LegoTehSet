package rebrickableAPI;

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

    public static OrderingType getOrderingTypeFromString(String jsonProperty) {
        return switch (jsonProperty) {
            case "year" -> YEAR;
            case "name" -> NAME;
            case "num_parts" -> NUM_PARTS;
            default -> throw new RuntimeException("Invalid value");
        };
    }
}
