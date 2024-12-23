package org.lts.lego_teh_set.commands;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/// An object that can return data about a slash command.
///
/// Later, such objects are collected by the Spring Framework and converted into {@link CommandData}.
///
/// # Implementation
///
/// The implementation must return metadata ({@link CommandData}) about the command,
/// which includes its id and arguments.
///
/// It is recommended to add such metadata to {@link SlashCommands} and {@link OptionDates} enums
///
/// ## Example
///
/// ```java
///
/// @Override
/// public CommandData getCommandData() {
///         return Commands.slash(SlashCommands.COMMAND_LIST.getName(),
///                 SlashCommands.COMMAND_LIST.getDescription());
/// }
///```
public interface CommandDataSupplier {
    /// @return slash command metadata
    CommandData getCommandData();
}
