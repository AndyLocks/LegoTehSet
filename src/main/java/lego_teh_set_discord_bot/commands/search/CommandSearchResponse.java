package lego_teh_set_discord_bot.commands.search;

import net.dv8tion.jda.api.interactions.components.buttons.Button;
import rebrickableAPI.returned_objects.Set;

import java.util.List;
import java.util.Objects;

/**
 * Response from the command handler.
 * Contains information to be displayed in discord chat.
 * <p>
 * This answer is given by the {@link SearchCommandHandler}.
 * The response is requested by the {@link CommandSearch}.
 */
public class CommandSearchResponse {
    private final List<Button> buttonList;
    private final Set set;
    private final int currentIndex;
    private final int maxIndex;

    /**
     * @param buttonList list of buttons
     * @param set lego set
     * @param currentIndex is needed to display the number of pages
     * @param maxIndex is needed to display the maximum page
     * @see Set
     */
    public CommandSearchResponse(List<Button> buttonList, Set set, int currentIndex, int maxIndex) {
        this.buttonList = buttonList;
        this.set = set;
        this.currentIndex = currentIndex;
        this.maxIndex = maxIndex;
    }

    public Set getCurrentSet() {
        return this.set;
    }

    /**
     * Buttons to display in chat.
     * Some of them may or may not be activated,
     * depending on, where the cursor is located.
     * @return list of valid buttons
     */
    public List<Button> getButtonList() {
        return buttonList;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getMaxIndex() {
        return maxIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandSearchResponse that = (CommandSearchResponse) o;
        return currentIndex == that.currentIndex && maxIndex == that.maxIndex && Objects.equals(buttonList, that.buttonList) && Objects.equals(set, that.set);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buttonList, set, currentIndex, maxIndex);
    }

    @Override
    public String toString() {
        return "CommandSearchResponse{" +
                "buttonList=" + buttonList +
                ", set=" + set +
                ", currentIndex=" + currentIndex +
                ", maxIndex=" + maxIndex +
                '}';
    }
}
