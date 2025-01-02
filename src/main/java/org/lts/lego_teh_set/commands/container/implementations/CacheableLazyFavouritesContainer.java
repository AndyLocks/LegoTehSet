package org.lts.lego_teh_set.commands.container.implementations;

import org.lts.Api;
import org.lts.lego_teh_set.commands.container.AbstractContainer;
import org.lts.lego_teh_set.commands.container.ContainerEmbed;
import org.lts.lego_teh_set.rebrickableAPI.dto.Set;
import org.lts.lego_teh_set.repository.SetRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/// Implementation of {@link AbstractContainer}
///
/// Contains ids of sets.
/// During a [ContainerEmbed] request with [#currentEmbed()],
/// it makes a request with [SetRepository] and generates a container on the fly
///
/// It saves the generated containers and does not make a request to the [SetRepository] two times.
public class CacheableLazyFavouritesContainer extends AbstractContainer {

    private final AtomicInteger index = new AtomicInteger(0);
    private final List<SetAndSetNumber> setNumbers = new ArrayList<>();
    private static SetRepository setRepository;
    private final String memberNickname;

    /// @param memberNickname the username that will be displayed in the container ([ContainerEmbed]).
    /// @param sets           sets that the container must contain
    /// @return a {@link CacheableLazyFavouritesContainer} that contains all `sets` converted to {@link ContainerEmbed}
    public static CacheableLazyFavouritesContainer fromSetList(final String memberNickname, final List<Api.FavouriteSet> sets, final SetRepository setRepository) {
        return new CacheableLazyFavouritesContainer(memberNickname, sets.stream()
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

    /// Creates a {@link CacheableLazyFavouritesContainer} that contains all `sets` converted to {@link ContainerEmbed}
    ///
    /// @param sets sets that the container must contain
    public CacheableLazyFavouritesContainer(final String memberNickname, final List<Set> sets, final SetRepository setRepository) {
        this.memberNickname = memberNickname;
        CacheableLazyFavouritesContainer.setRepository = setRepository;
        sets.stream()
                .map(set -> new SetAndSetNumber(set.number(), null))
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
        var setAndSetNumber = setNumbers.get(index.get());

        if (setAndSetNumber.getContainerEmbed() != null) return setAndSetNumber.getContainerEmbed();

        var set = setRepository.setFromId(setAndSetNumber.getSetNumber());

        var contatiner = ContainerEmbed.builder()
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

        setAndSetNumber.setContainerEmbed(contatiner);

        return contatiner;
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

    private static final class SetAndSetNumber implements Serializable {

        private String setNumber;
        private ContainerEmbed containerEmbed = null;

        public SetAndSetNumber(String setNumber, ContainerEmbed containerEmbed) {
            this.setNumber = setNumber;
            this.containerEmbed = containerEmbed;
        }

        public String getSetNumber() {
            return setNumber;
        }

        public void setSetNumber(String setNumber) {
            this.setNumber = setNumber;
        }

        public ContainerEmbed getContainerEmbed() {
            return containerEmbed;
        }

        public void setContainerEmbed(ContainerEmbed containerEmbed) {
            this.containerEmbed = containerEmbed;
        }

    }

}
