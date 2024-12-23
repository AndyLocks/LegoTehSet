package org.lts.lego_teh_set.commands.container;

import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.lts.lego_teh_set.commands.Buttons;

import java.io.Serializable;
import java.util.List;

/// Represents an abstract entity for storing,
/// displaying, and managing a list of data to be displayed in a discord chat.
///
/// ### Examples
///
/// List of awards for a particular server,
/// rank of participants,
/// in general anything that can be scrolled with arrows and
/// anything that can contain information for display.
///
/// @see ContainerDataProvider
/// @see PageNavigable
public abstract class AbstractContainer implements Serializable, PageNavigable, ContainerDataProvider {

    public static final int EMBED_COLOR = 10190047;

    private static final Button backwardButton = Buttons.BACKWARD.getButton();
    private static final Button forwardButton = Buttons.FORWARD.getButton();
    private static final Button toStart = Buttons.TO_START.getButton();
    private static final Button toEnd = Buttons.TO_END.getButton();
    private static final Button page = Buttons.PAGE.getButton();

    @Override
    public List<Button> buttons() {
        return List.of(
                this.hasPrevious() ? toStart : toStart.asDisabled(),
                this.hasPrevious() ? backwardButton : backwardButton.asDisabled(),
                this.hasNext() ? forwardButton : forwardButton.asDisabled(),
                this.hasNext() ? toEnd : toEnd.asDisabled(),
                page
        );
    }

}
