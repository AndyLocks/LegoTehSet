package lego_teh_set_discord_bot.commands.full_search;

import lego_teh_set_discord_bot.component_creators.EmbedBuilderCreator;
import net.dv8tion.jda.api.EmbedBuilder;
import rebrickableAPI.returned_objects.Set;

import java.util.List;

public class RequestMessage {

    private volatile List<Set> setList;
    private volatile int currentIndex = 0;

    public RequestMessage(List<Set> setList) {
        this.setList = setList;
    }

    public EmbedBuilder getCurrentEmbedBuilder() {
        return EmbedBuilderCreator.Companion.getEmbedBuilder(
                this.setList.get(this.currentIndex)
        );
    }

    public Set getCurrentSet() {
        return this.setList.get(this.currentIndex);
    }

    public synchronized void next() {
        this.currentIndex++;
    }

    public synchronized void prev() {
        this.currentIndex--;
    }
    public synchronized boolean hasNext() {
        return this.setList.size()-1 > this.currentIndex;
    }
    public synchronized boolean hasPrev() {
        return this.currentIndex > 0;
    }
    public synchronized EmbedBuilder getFirstEmbedBuilder() {
        this.currentIndex = 0;
        return EmbedBuilderCreator.Companion.getEmbedBuilder(
                this.setList.get(0)
        );
    }
    public synchronized EmbedBuilder getLastEmbedBuilder() {
        this.currentIndex = this.setList.size()-1;
        return EmbedBuilderCreator.Companion.getEmbedBuilder(
                this.setList.get(this.currentIndex)
        );
    }
}
