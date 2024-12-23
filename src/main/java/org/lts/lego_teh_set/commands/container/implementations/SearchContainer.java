package org.lts.lego_teh_set.commands.container.implementations;

import org.lts.lego_teh_set.commands.container.AbstractContainer;
import org.lts.lego_teh_set.commands.container.ContainerEmbed;
import org.lts.lego_teh_set.rebrickableAPI.dto.Set;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/// Implementation of {@link AbstractContainer}
///
/// Contains a list of sets ({@link Set}) and allows you to manage them
public class SearchContainer extends AbstractContainer {

    private final AtomicInteger index = new AtomicInteger(0);
    private final List<ContainerEmbed> embeds = new ArrayList<>();

    /// @param sets sets that the container must contain
    /// @return a {@link SearchContainer} that contains all `sets` converted to {@link ContainerEmbed}
    public static SearchContainer fromSetList(final List<Set> sets) {
        return new SearchContainer(sets);
    }

    /// Creates a {@link SearchContainer} that contains all `sets` converted to {@link ContainerEmbed}
    ///
    /// @param sets sets that the container must contain
    public SearchContainer(final List<Set> sets) {
        for (int i = 0; i < sets.size(); i++) {
            var set = sets.get(i);
            embeds.add(ContainerEmbed.builder()
                    .color(EMBED_COLOR)
                    .imageUrl(set.setImageUrl())
                    .footer(String.format("Year: %d", set.year()))
                    .description(String.format("""
                            ### %d/%d
                            # [%s](%s)
                            ## %s
                            Parts: %d""", i + 1, sets.size(), set.name(), set.setUrl(), set.number(), set.parts()))
                    .build());
        }
    }

    @Override
    public void setPage(int page) {
        if (page < 0 || page >= embeds.size())
            throw new IllegalArgumentException("The page number cannot be less than 0 or greater than the number of sets.");

        index.set(page);
    }

    @Override
    public ContainerEmbed currentEmbed() {
        return embeds.get(index.get());
    }

    @Override
    public void next() {
        if (hasNext()) index.getAndIncrement();
    }

    @Override
    public void previous() {
        if (hasPrevious()) index.getAndDecrement();
    }

    @Override
    public boolean hasNext() {
        return embeds.size() - 1 > index.get();
    }

    @Override
    public boolean hasPrevious() {
        return index.get() > 0;
    }

    @Override
    public void toEnd() {
        index.set(embeds.size() - 1);
    }

    @Override
    public void toStart() {
        index.set(0);
    }

}
