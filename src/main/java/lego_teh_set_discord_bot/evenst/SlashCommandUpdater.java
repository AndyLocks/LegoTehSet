package lego_teh_set_discord_bot.evenst;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

/**
 * Обновляет список слэш-команд у discord бота.
 * Также добавляет аргументы в команды.
 */
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

        OptionData theme = new OptionData(OptionType.INTEGER, "theme", "Theme")
                .addChoice("Technic", 1)
                .addChoice("Star Wars", 18)
                .addChoice("City", 52)
                .addChoice("Creator", 22)
                .addChoice("Bionicle", 324)
                .addChoice("Ninjago", 435)
                .addChoice("Minecraft", 577)
                .addChoice("Duplo", 504)
                .addChoice("Friends", 216);

        commandData.add(
                Commands.slash("search", "find all lego sets")
                        .addOptions(optionDataSet, orderingTypeOptionData)
        );

        commandData.add(
                Commands.slash("command_list", "command list")
        );

        commandData.add(
                Commands.context(Command.Type.MESSAGE, "set")
        );

        commandData.add(
                Commands.slash("random", "get random set")
                        .addOptions(theme)
        );

        commandData.add(
                Commands.context(Command.Type.MESSAGE, "full_search")
        );

        event.getJDA().updateCommands().addCommands(
                commandData
        ).queue();
    }
}
