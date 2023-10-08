package rebrickableAPI;

import rebrickableAPI.exceptions.InvalidThemeId;
import rebrickableAPI.exceptions.InvalidThemeName;

import javax.management.openmbean.SimpleType;

public enum Theme {

    TECHNIC("Technic", 1),
    STAR_WARS("Star Wars", 18),
    CITY("City", 52);
    private int themeId;
    private String themeName;
    Theme(String themeName, int themeId) {
        this.themeName = themeName;
        this.themeId = themeId;
    }

    public static Theme getThemeFromId(int themeId) throws InvalidThemeId {
        return switch (themeId) {
            case 1 -> TECHNIC;
            case 18 -> STAR_WARS;
            case 52 -> CITY;
            default -> throw new InvalidThemeId("Invalid input");
        };
    }

    public static Theme getThemeFromName(String themeName) throws InvalidThemeName {
        return switch (themeName) {
            case "Technic" -> TECHNIC;
            case "Star Wars" -> STAR_WARS;
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
