package rebrickableAPI;

/**
 * Тип сортировки наборов.
 * <p>
 * Необходим для запросов к <a href="https://rebrickable.com/api/v3/docs/">rebrickable API</a>
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
     * Выдает тип по названию
     *
     * @param jsonProperty название свойства
     * @throws RuntimeException если не правильное название свойства
     * @return тип для сортировки
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
