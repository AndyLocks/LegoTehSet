package rebrickableAPI;

public enum OrderingType {
    YEAR("year"),
    NAME("name"),
    NUM_PARTS("num_parts");
    private String jsonProperty;

    OrderingType(String jsonProperty) {
        this.jsonProperty = jsonProperty;
    }

    public String getJsonProperty() {
        return this.jsonProperty;
    }

    public static OrderingType getOrderingTypeFromString(String jsonProperty) throws Exception {
        return switch (jsonProperty) {
            case "year" -> YEAR;
            case "name" -> NAME;
            case "num_parts" -> NUM_PARTS;
            default -> throw new Exception("Invalid value");
        };
    }
}
