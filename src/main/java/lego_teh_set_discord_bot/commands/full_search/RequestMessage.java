package lego_teh_set_discord_bot.commands.full_search;

import lego_teh_set_discord_bot.component_creators.EmbedBuilderCreator;
import net.dv8tion.jda.api.EmbedBuilder;
import rebrickableAPI.returned_objects.Set;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestMessage {

    private List<Set> setList;
    private AtomicInteger currentIndex = new AtomicInteger(0);

    public RequestMessage(List<Set> setList) {
        this.setList = setList;
    }

    public EmbedBuilder getCurrentEmbedBuilder() {
        return EmbedBuilderCreator.Companion.getEmbedBuilder(
                this.setList.get(this.currentIndex.get())
        );
    }

    public EmbedBuilder getCurrentEmbedBuilderWithPageNumber() {
        EmbedBuilder embedBuilder = this.getCurrentEmbedBuilder();
        embedBuilder.setTitle(new StringBuilder().append(this.currentIndex.get()+1).append(" / ").append(this.setList.size()).toString());
        return embedBuilder;
    }

    public Set getCurrentSet() {
        return this.setList.get(this.currentIndex.get());
    }

    public void next() {
        if (currentIndex.get() < this.setList.size()-1)
            this.currentIndex.getAndIncrement();
    }

    public void prev() {
        if(this.currentIndex.get() != 0)
            this.currentIndex.getAndDecrement();
    }
    public boolean hasNext() {
        return this.setList.size()-1 > this.currentIndex.get();
    }
    public boolean hasPrev() {
        return this.currentIndex.get() > 0;
    }
    public EmbedBuilder getFirstEmbedBuilder() {
        this.currentIndex.set(0);
        return this.getCurrentEmbedBuilderWithPageNumber();
    }
    public EmbedBuilder getLastEmbedBuilder() {
        this.currentIndex.set(this.setList.size()-1);
        return this.getCurrentEmbedBuilderWithPageNumber();
    }
    public int size() {
        return this.setList.size();
    }

    public void setCurrentIndex(int index) {
        if(index > this.setList.size()-1 || index < 0)
            throw new IndexOutOfBoundsException("Index out of range");
        this.currentIndex.set(index);
    }
}
