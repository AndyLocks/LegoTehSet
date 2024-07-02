package org.lts.lego_teh_set.command.search;

import org.lts.lego_teh_set.rebrickableAPI.returned_objects.Set;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Contains and manages sets.
 * <p>
 * Represents a list of {@link Set}.
 * Also allows you to move the cursor and get the current set.
 */
public class SetsContainer {

    private final List<Set> setList;
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    public SetsContainer(List<Set> setList) {
        this.setList = setList;
    }

    /**
     * @return the set the cursor is looking at
     */
    public Set getCurrentSet() {
        if (setList.isEmpty())
            throw new EmptySetsContainerException("List is empty");
        return this.setList.get(this.currentIndex.get());
    }

    /**
     * Moves the cursor one forward.
     */
    public void next() {
        if (currentIndex.get() < this.setList.size() - 1)
            this.currentIndex.getAndIncrement();
    }

    /**
     * Moves the cursor one backward.
     */
    public void prev() {
        if (this.currentIndex.get() != 0)
            this.currentIndex.getAndDecrement();
    }

    /**
     * @return true if there is the next element
     */
    public boolean hasNext() {
        return this.setList.size() - 1 > this.currentIndex.get();
    }

    /**
     * @return true if there is a previous element
     */
    public boolean hasPrev() {
        return this.currentIndex.get() > 0;
    }

    /**
     * Moves the cursor to the first set.
     */
    public void toStart() {
        this.currentIndex.set(0);
    }

    /**
     * Moves the cursor to the last set.
     */
    public void toEnd() {
        if (setList.isEmpty())
            throw new EmptySetsContainerException("List is empty");
        this.currentIndex.set(this.setList.size() - 1);
    }

    /**
     * Sets the current cursor.
     *
     * @param index desired index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public void setCurrentIndex(int index) {
        if (index > this.setList.size() - 1 || index < 0)
            throw new IndexOutOfBoundsException("Index out of range");
        this.currentIndex.set(index);
    }

    /**
     * @return set array size
     */
    public int size() {
        return this.setList.size();
    }

    /**
     * @return cursor position
     */
    public int getCurrentIndex() {
        return this.currentIndex.get();
    }

}
