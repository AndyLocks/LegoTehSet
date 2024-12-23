package org.lts.lego_teh_set.events;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.lts.lego_teh_set.commands.CommandDataSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/// Collects all objects that implement {@link CommandDataSupplier} throughout the application and updates the bot's slash command data.
@Component
public class SlashCommandUpdater extends ListenerAdapter {

    private final Collection<CommandDataSupplier> commandDataSuppliers;

    @Autowired
    public SlashCommandUpdater(Collection<CommandDataSupplier> commandDataSuppliers) {
        this.commandDataSuppliers = commandDataSuppliers;
    }

    @Override
    public void onReady(ReadyEvent event) {
        event.getJDA().updateCommands()
                .addCommands(commandDataSuppliers.stream().map(CommandDataSupplier::getCommandData).toList())
                .queue();
    }

}
