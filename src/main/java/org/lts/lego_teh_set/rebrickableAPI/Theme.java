package org.lts.lego_teh_set.rebrickableAPI;

import org.lts.lego_teh_set.rebrickableAPI.exceptions.InvalidThemeName;
import org.lts.lego_teh_set.rebrickableAPI.exceptions.InvalidThemeId;

/// A lego set's theme.
///
/// Not used in {@link org.lts.lego_teh_set.rebrickableAPI.dto.Set}.
/// It is only needed for [Rebrickable API](https://rebrickable.com/api/v3/docs/).
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

    /// Gives the theme of the lego set by id.
    ///
    /// @param themeId theme id in the [rebrickable database](https://rebrickable.com/api/v3/docs/)
    /// @return gives a theme
    /// @throws InvalidThemeId if the theme is not found by ID
    public static Theme fromId(int themeId) throws InvalidThemeId {
        for (var theme : values())
            if (theme.getThemeId() == themeId) return theme;

        throw new InvalidThemeId("Invalid input");
    }

    /// Gives a theme by name.
    ///
    /// @param themeName theme name
    /// @return gives a theme
    /// @throws InvalidThemeName if the theme is not found by name
    public static Theme fromName(String themeName) throws InvalidThemeName {
        for (var theme : values())
            if (theme.getThemeName().equals(themeName)) return theme;

        throw new InvalidThemeName("Invalid input");
    }

    public int getThemeId() {
        return themeId;
    }

    /// @return normal name like `Technic` or `City`
    public String getThemeName() {
        return themeName;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "themeId=" + themeId +
                ", themeName='" + themeName + '\'' +
                '}';
    }

}
