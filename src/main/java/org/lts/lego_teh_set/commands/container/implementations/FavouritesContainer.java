package org.lts.lego_teh_set.commands.container.implementations;

import org.lts.Api;
import org.lts.lego_teh_set.commands.container.AbstractContainer;
import org.lts.lego_teh_set.commands.container.ContainerEmbed;
import org.lts.lego_teh_set.rebrickableAPI.dto.Set;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/// Implementation of {@link AbstractContainer}
///
/// Stores and manages a list of favorite sets ([Set])
public class FavouritesContainer extends AbstractContainer {

    private final AtomicInteger index = new AtomicInteger(0);
    private final List<ContainerEmbed> embeds = new ArrayList<>();

    /// @param memberNickname the username that will be displayed in the container ([ContainerEmbed]).
    /// @param sets           sets that the container must contain
    /// @return a {@link FavouritesContainer} that contains all `sets` converted to {@link ContainerEmbed}
    public static FavouritesContainer fromSetList(final String memberNickname, final List<Api.FavouriteSet> sets) {
        return new FavouritesContainer(memberNickname, sets.stream()
                .map(Api.FavouriteSet::getSet)
                .map(set -> Set.builder()
                        .number(set.getNumber())
                        .name(set.getName())
                        .year(set.getYear())
                        .themeId(set.getThemeId())
                        .parts(set.getParts())
                        .setImageUrl(set.getSetImageUrl())
                        .setUrl(set.getSetUrl())
                        .lastModifiedDate(set.getLastModifiedDate())
                        .build())
                .toList());
    }

    /// Creates a {@link FavouritesContainer} that contains all `sets` converted to {@link ContainerEmbed}
    ///
    /// @param sets sets that the container must contain
    public FavouritesContainer(final String memberNickname, final List<Set> sets) {
        for (int i = 0; i < sets.size(); i++) {
            var set = sets.get(i);
            embeds.add(ContainerEmbed.builder()
                    .color(EMBED_COLOR)
                    .imageUrl(set.setImageUrl())
                    .footer(String.format("Year: %d", set.year()))
                    .description(String.format("""
                            # %s's favorite sets :star:
                            ### %d/%d
                            # [%s](%s)
                            ## %s
                            Parts: %d""", memberNickname, i + 1, sets.size(), set.name(), set.setUrl(), set.number(), set.parts()))
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
