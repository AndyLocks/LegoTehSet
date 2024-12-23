package org.lts.lego_teh_set.commands;

/// All slash command metadata in the application
public enum SlashCommands {
    /// @see org.lts.lego_teh_set.commands.implementations.CommandCommandList
    COMMAND_LIST("command_list", "command list"),
    /// @see org.lts.lego_teh_set.commands.implementations.CommandRandom
    RANDOM("random", "get random set"),
    /// @see org.lts.lego_teh_set.commands.implementations.CommandSearch
    SEARCH("search", "find all lego sets");

    private String name;
    private String description;

    SlashCommands(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
