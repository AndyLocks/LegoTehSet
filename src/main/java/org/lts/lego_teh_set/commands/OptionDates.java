package org.lts.lego_teh_set.commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

/// All arguments for slash commands ({@link SlashCommands}) in the application
public enum OptionDates {
    /// A request argument for `search` command ({@link org.lts.lego_teh_set.commands.implementations.CommandSearch})
    REQUEST(new OptionData(OptionType.STRING,
            "request",
            "what to find",
            true)),
    ORDERING_TYPE(new OptionData(OptionType.STRING, "ordering", "Which field to use when ordering the results.")
            .addChoice("Year", "year")
            .addChoice("Name", "name")
            .addChoice("Number of parts", "num_parts")),
    THEME(new OptionData(OptionType.INTEGER, "theme", "Theme")
            .addChoice("Technic", 1)
            .addChoice("Star Wars", 18)
            .addChoice("City", 52)
            .addChoice("Creator", 22)
            .addChoice("Bionicle", 324)
            .addChoice("Ninjago", 435)
            .addChoice("Minecraft", 577)
            .addChoice("Duplo", 504)
            .addChoice("Friends", 216));

    private OptionData optionData;

    OptionDates(OptionData optionData) {
        this.optionData = optionData;
    }

    public OptionData getOptionData() {
        return optionData;
    }

    public void setOptionData(OptionData optionData) {
        this.optionData = optionData;
    }

}
