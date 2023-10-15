package lego_teh_set_discord_bot.commands.search;

import rebrickableAPI.returned_objects.Set;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SetsContainer {
    private final List<Set> setList;
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    public SetsContainer(List<Set> setList) {
        this.setList = setList;
    }

    public Set getCurrentSet() {
        if(setList.isEmpty())
            throw new EmptySetsContainerException("List is empty");
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
    public void toStart() {
        this.currentIndex.set(0);
    }

    public void toEnd() {
        if(setList.isEmpty())
            throw new EmptySetsContainerException("List is empty");
        this.currentIndex.set(this.setList.size()-1);
    }

    public void setCurrentIndex(int index) {
        if(index > this.setList.size()-1 || index < 0)
            throw new IndexOutOfBoundsException("Index out of range");
        this.currentIndex.set(index);
    }

    public int size() {
        return this.setList.size();
    }
    public int getCurrentIndex() {
        return this.currentIndex.get();
    }
}
