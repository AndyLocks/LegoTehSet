package org.lts.lego_teh_set.commands.container;

import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.List;

/// Object that manages container data
public interface ContainerDataProvider {
    /// @return all current buttons to show
    List<Button> buttons();

    /// @return current embed to show
    ContainerEmbed currentEmbed();
}
