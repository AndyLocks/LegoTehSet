package lego_teh_set_discord_bot.evenst;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class SlashCommandUpdater extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {

        List<CommandData> commandData = new ArrayList<>();

        OptionData optionDataSet = new OptionData(
                OptionType.STRING,
                "request",
                "what to find",
                true
        );

        OptionData orderingTypeOptionData = new OptionData(OptionType.STRING, "ordering", "Which field to use when ordering the results.")
                .addChoice("Year", "year")
                .addChoice("Name", "name")
                .addChoice("Number of parts", "num_parts");

        commandData.add(
                Commands.slash("set", "find a lego technic set")
                        .addOptions(
                                optionDataSet
                        )
        );

        commandData.add(
                Commands.slash("full_search", "find all lego sets")
                        .addOptions(optionDataSet, orderingTypeOptionData)
        );

        commandData.add(
                Commands.slash("command_list", "command list")
        );

        event.getJDA().updateCommands().addCommands(
                commandData
        ).queue();
    }
}
