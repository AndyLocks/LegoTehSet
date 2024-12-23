package org.lts.lego_teh_set.commands;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

/// All buttons in the application
public enum Buttons {
    TO_START(Button.secondary("search_first", Emoji.fromFormatted("<:rewind_lts:1258860938904338432>"))),
    TO_END(Button.secondary("search_last", Emoji.fromFormatted("<:fast_forward_lts:1258860751771275349>"))),
    FORWARD(Button.secondary("search_arrow_forward", Emoji.fromFormatted("<:arrow_forward_lts:1258861075022086145>"))),
    BACKWARD(Button.secondary("search_arrow_backward", Emoji.fromFormatted("<:arrow_backward_lts:1258861195385901138>"))),
    PAGE(Button.secondary("search_text_input_button", "page"));

    private final Button button;

    /// @param id id of the button by which the search is performed
    /// @return null if a button with the id was not found
    public static Buttons fromId(String id) {
        for (var button : values())
            if (button.getButton().getLabel().equals(id)) return button;

        return null;
    }

    /// @param button a button that {@link Buttons} object must contain
    /// @return {@link Buttons} object if it contains the provided button,
    /// null if a button was not found
    public static Buttons fromButton(Button button) {
        for (var btn : values())
            if (btn.getButton().equals(button)) return btn;

        return null;
    }

    Buttons(Button button) {
        this.button = button;
    }

    public Button getButton() {
        return button;
    }

}
