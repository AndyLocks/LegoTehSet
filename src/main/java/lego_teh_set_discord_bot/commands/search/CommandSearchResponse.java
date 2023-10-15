package lego_teh_set_discord_bot.commands.search;

import net.dv8tion.jda.api.interactions.components.buttons.Button;
import rebrickableAPI.returned_objects.Set;

import java.util.List;

public class CommandSearchResponse {
    private final List<Button> buttonList;
    private final Set set;
    private final int currentIndex;
    private final int maxIndex;

    public CommandSearchResponse(List<Button> buttonList, Set set, int currentIndex, int maxIndex) {
        this.buttonList = buttonList;
        this.set = set;
        this.currentIndex = currentIndex;
        this.maxIndex = maxIndex;
    }

    public Set getCurrentSet() {
        return this.set;
    }

    public List<Button> getButtonList() {
        return buttonList;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getMaxIndex() {
        return maxIndex;
    }
}
