package org.lts.lego_teh_set.rebrickableAPI;


import org.lts.lego_teh_set.rebrickableAPI.exceptions.InvalidThemeName;
import org.lts.lego_teh_set.rebrickableAPI.exceptions.InvalidThemeId;

/**
 * A lego set's theme.
 * <p>
 * Not used in {@link org.lts.lego_teh_set.rebrickableAPI.returned_objects.Set}.
 * It is only needed for database requests.
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
     * Gives the theme of the lego set by id.
     *
     * @param themeId theme ID in the <a href="https://rebrickable.com/api/v3/docs/">rebrickable database</a>
     * @return gives a theme
     * @throws InvalidThemeId if the theme is not found by ID
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
     * Gives a theme by name.
     *
     * @param themeName theme name
     * @return gives a theme
     * @throws InvalidThemeName if the theme is not found by name
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

    @Override
    public String toString() {
        return "Theme{" +
                "themeId=" + themeId +
                ", themeName='" + themeName + '\'' +
                '}';
    }

    public String getThemeName() {
        return themeName;
    }
}
