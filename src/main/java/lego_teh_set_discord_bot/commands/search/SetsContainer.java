package lego_teh_set_discord_bot.commands.search;

import rebrickableAPI.returned_objects.Set;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  Содержит наборы и управляет ими.
 *  <p>
 *  Представляет собой список наборов {@link Set}
 *  Также дает возможность перемещать курсор и получать текущий набор
 */
public class SetsContainer {
    private final List<Set> setList;
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    public SetsContainer(List<Set> setList) {
        this.setList = setList;
    }

    /**
     * @return набор, на который смотрит курсор
     */
    public Set getCurrentSet() {
        if(setList.isEmpty())
            throw new EmptySetsContainerException("List is empty");
        return this.setList.get(this.currentIndex.get());
    }

    /**
     * Перемещает курсор на один вперед
     */
    public void next() {
        if (currentIndex.get() < this.setList.size()-1)
            this.currentIndex.getAndIncrement();
    }

    /**
     * Перемещает курсор на один назад
     */
    public void prev() {
        if(this.currentIndex.get() != 0)
            this.currentIndex.getAndDecrement();
    }

    /**
     * @return true если есть следующий элемент
     */
    public boolean hasNext() {
        return this.setList.size()-1 > this.currentIndex.get();
    }

    /**
     * @return true если есть предыдущий элемент
     */
    public boolean hasPrev() {
        return this.currentIndex.get() > 0;
    }

    /**
     * Переносит курсор на самый первый набор
     */
    public void toStart() {
        this.currentIndex.set(0);
    }

    /**
     * Переносит набор на самый последний набор
     */
    public void toEnd() {
        if(setList.isEmpty())
            throw new EmptySetsContainerException("List is empty");
        this.currentIndex.set(this.setList.size()-1);
    }

    /**
     * Устанавливает текущий индекс
     *
     * @param index желаемый индекс
     * @throws IndexOutOfBoundsException если индекс выходит за рамки дозволенного
     */
    public void setCurrentIndex(int index) {
        if(index > this.setList.size()-1 || index < 0)
            throw new IndexOutOfBoundsException("Index out of range");
        this.currentIndex.set(index);
    }

    /**
     * @return размер массива с наборами
     */
    public int size() {
        return this.setList.size();
    }

    /**
     * @return индекс текущего набора
     */
    public int getCurrentIndex() {
        return this.currentIndex.get();
    }
}
