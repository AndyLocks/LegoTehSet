package org.lts.lego_teh_set.commands;

/// All {@link net.dv8tion.jda.api.interactions.components.text.TextInput} metadata in the application
public enum TextInputs {
    /// Page button
    ///
    /// @see org.lts.lego_teh_set.commands.implementations.CommandSearch
    /// @see org.lts.lego_teh_set.events.AbstractContainerButtonsInteraction
    PAGE("search_text_input", "Page");

    private String id;
    private String label;

    TextInputs(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

}
