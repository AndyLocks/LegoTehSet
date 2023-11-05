package rebrickableAPI;

import rebrickableAPI.exceptions.InvalidThemeId;
import rebrickableAPI.exceptions.InvalidThemeName;

/**
 * Тематика набора.
 * <p>
 * Не используется в {@link rebrickableAPI.returned_objects.Set}.
 * Он нужен только для запросов в базу данных для получения наборов по теме.
 */
public enum Theme {

    TECHNIC("Technic", 1),
    STAR_WARS("Star Wars", 18),
    CREATOR("Creator", 22),
    FRIENDS("Friends", 216),
    BIONICLE("Bionicle", 324),
    NINJAGO("Ninjago", 435),
    DUPLO("Duplo", 504),
    MINECRAFT("Minecraft", 577),
    CITY("City", 52);
    private final int themeId;
    private final String themeName;
    Theme(String themeName, int themeId) {
        this.themeName = themeName;
        this.themeId = themeId;
    }

    /**
     * Дает тематику по айди
     *
     * @param themeId айди темы в <a href="https://rebrickable.com/api/v3/docs/">базе данных rebrickable</a>
     * @return дает тематику
     * @throws InvalidThemeId если не найдена тема по айди
     */
    public static Theme getThemeFromId(int themeId) throws InvalidThemeId {
        return switch (themeId) {
            case 1 -> TECHNIC;
            case 18 -> STAR_WARS;
            case 22 -> CREATOR;
            case 504 -> DUPLO;
            case 577 -> MINECRAFT;
            case 324 -> BIONICLE;
            case 435 -> NINJAGO;
            case 216 -> FRIENDS;
            case 52 -> CITY;
            default -> throw new InvalidThemeId("Invalid input");
        };
    }

    /**
     * Дает тематику по имени
     *
     * @param themeName имя темы
     * @return дает тематику
     * @throws InvalidThemeName если не найдена тема по имени
     */
    public static Theme getThemeFromName(String themeName) throws InvalidThemeName {
        return switch (themeName) {
            case "Technic" -> TECHNIC;
            case "Star Wars" -> STAR_WARS;
            case "Creator" -> CREATOR;
            case "Bionicle" -> BIONICLE;
            case "Ninjago" -> NINJAGO;
            case "Minecraft" -> MINECRAFT;
            case "Duplo" -> DUPLO;
            case "Friends" -> FRIENDS;
            case "City" -> CITY;
            default -> throw new InvalidThemeName("Invalid input");
        };
    }

    public int getThemeId() {
        return themeId;
    }

    public String getThemeName() {
        return themeName;
    }
}
