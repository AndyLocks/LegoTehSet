package org.lts.lego_teh_set.commands.container.implementations;

import org.lts.Api;
import org.lts.lego_teh_set.commands.container.AbstractContainer;
import org.lts.lego_teh_set.commands.container.ContainerEmbed;
import org.lts.lego_teh_set.rebrickableAPI.dto.Set;
import org.lts.lego_teh_set.repository.SetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/// Implementation of {@link AbstractContainer}
///
/// Contains a list of sets ({@link Set}) and allows you to manage them
public class LazyFavouritesContainer extends AbstractContainer {

    private final AtomicInteger index = new AtomicInteger(0);
    private final List<String> setNumbers = new ArrayList<>();
    private static SetRepository setRepository;
    private final String memberNickname;

    /// @param sets sets that the container must contain
    /// @return a {@link LazyFavouritesContainer} that contains all `sets` converted to {@link ContainerEmbed}
    public static LazyFavouritesContainer fromSetList(final String memberNickname, final List<Api.FavouriteSet> sets, final SetRepository setRepository) {
        return new LazyFavouritesContainer(memberNickname, sets.stream()
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
                .toList(),
                setRepository);
    }

    /// Creates a {@link LazyFavouritesContainer} that contains all `sets` converted to {@link ContainerEmbed}
    ///
    /// @param sets sets that the container must contain
    public LazyFavouritesContainer(final String memberNickname, final List<Set> sets, final SetRepository setRepository) {
        this.memberNickname = memberNickname;
        LazyFavouritesContainer.setRepository = setRepository;
        sets.stream()
                .map(Set::number)
                .forEach(this.setNumbers::add);
    }

    @Override
    public void setPage(int page) {
        if (page < 0 || page >= setNumbers.size())
            throw new IllegalArgumentException("The page number cannot be less than 0 or greater than the number of sets.");

        index.set(page);
    }

    @Override
    public ContainerEmbed currentEmbed() {
        var set = setRepository.setFromId(setNumbers.get(index.get()));

        return ContainerEmbed.builder()
                .color(EMBED_COLOR)
                .imageUrl(set.setImageUrl())
                .footer(String.format("Year: %d", set.year()))
                .description(String.format("""
                            # %s's favorite sets :star:
                            ### %d/%d
                            # [%s](%s)
                            ## %s
                            Parts: %d""", memberNickname, index.get() + 1, setNumbers.size(), set.name(), set.setUrl(), set.number(), set.parts()))
                .build();
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
        return setNumbers.size() - 1 > index.get();
    }

    @Override
    public boolean hasPrevious() {
        return index.get() > 0;
    }

    @Override
    public void toEnd() {
        index.set(setNumbers.size() - 1);
    }

    @Override
    public void toStart() {
        index.set(0);
    }

}
