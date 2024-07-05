package org.lts.lego_teh_set.command.search;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.lts.lego_teh_set.ApplicationContextProvider;
import org.lts.lego_teh_set.rebrickableAPI.returned_objects.Set;
import org.lts.lego_teh_set.repository.SetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.lts.lego_teh_set.rebrickableAPI.OrderingType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Search command handler.
 * <p>
 * This is where all the logic of the search command is hidden.
 * This class is used by the {@link CommandSearch} to get the
 * {@link CommandSearchResponse}.
 */
public class SearchCommandHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchCommandHandler.class);
    private final ConcurrentMap<String, SetsContainer> setsContainerMap = new ConcurrentHashMap<>();
    private final Button firstButton = Button.secondary("search_first",
            Emoji.fromFormatted("<:rewind_lts:1258860938904338432>"));
    private final Button lastButton = Button.secondary("search_last",
            Emoji.fromFormatted("<:fast_forward_lts:1258860751771275349>"));
    private final Button arrowBackwardButton = Button.secondary("search_arrow_backward",
            Emoji.fromFormatted("<:arrow_backward_lts:1258861195385901138>"));
    private final Button arrowForwardButton = Button.secondary("search_arrow_forward",
            Emoji.fromFormatted("<:arrow_forward_lts:1258861075022086145>"));
    private final Button pageButton = Button.secondary("search_text_input_button", "page");
    private SetRepository setRepository = ApplicationContextProvider.getApplicationContext()
            .getBean("setRepository", SetRepository.class);

    /**
     * Get actual buttons.
     * <p>
     * The relevance of the buttons depends on the current index.
     *
     * @param setsContainer set container
     * @return returns the list of buttons depending on the current index
     * @see SetsContainer
     */
    private List<Button> getButtonList(SetsContainer setsContainer) {

        List<Button> buttonList = new ArrayList<>();
        if (setsContainer.hasPrev()) {
            buttonList.add(firstButton);
            buttonList.add(arrowBackwardButton);
        } else {
            buttonList.add(firstButton.asDisabled());
            buttonList.add(arrowBackwardButton.asDisabled());
        }
        if (setsContainer.hasNext()) {
            buttonList.add(arrowForwardButton);
            buttonList.add(lastButton);
        } else {
            buttonList.add(arrowForwardButton.asDisabled());
            buttonList.add(lastButton.asDisabled());
        }
        buttonList.add(pageButton);

        LOGGER.info("full_search_right: Message Hash Map size: {}", setsContainerMap.size());

        return buttonList;
    }

    /**
     * Initialization of everything necessary for the search command to work.
     * <p>
     * Gets everything ready to go and adds {@link SetsContainer} to the
     * {@link #setsContainerMap}.
     *
     * @param search        user search request
     * @param orderingType  the theme on which to sort the sets. Can also be null
     * @param interactionId a unique id to interact with discord. It's also the key
     *                      to {@link #setsContainerMap}
     * @return returns a response
     */
    public CommandSearchResponse init(String search, OrderingType orderingType, String interactionId) {

        List<Set> resultSetList;

        if (orderingType == null)
            resultSetList = this.setRepository.getSearchResult(search);
        else
            resultSetList = this.setRepository.getSearchResult(search, orderingType);

        SetsContainer setsContainer = new SetsContainer(resultSetList);

        this.setsContainerMap.put(interactionId, setsContainer);

        LOGGER.info("full_search_right: Message Hash Map size: {}", setsContainerMap.size());

        return new CommandSearchResponse(
                this.getButtonList(setsContainer),
                setsContainer.getCurrentSet(),
                setsContainer.getCurrentIndex(),
                setsContainer.size());
    }

    /**
     * Interaction with the forward button.
     * <p>
     * Changes {@link SetsContainer} in {@link #setsContainerMap}.
     * Also moves the index one element forward.
     *
     * @param interactionId a unique id to interact with discord. It's also the key
     *                      to {@link #setsContainerMap}
     * @return returns a response with the index already moved
     * @throws EmptySetsContainerException if the container is empty
     */
    public CommandSearchResponse arrowForward(String interactionId) {

        SetsContainer setsContainer = this.setsContainerMap.get(interactionId);
        if (setsContainer == null)
            throw new EmptySetsContainerException("Sets Container is null");
        setsContainer.next();
        Set set = setsContainer.getCurrentSet();

        LOGGER.info("full_search_right: Message Hash Map size: {}", setsContainerMap.size());

        return new CommandSearchResponse(
                this.getButtonList(setsContainer),
                set,
                setsContainer.getCurrentIndex(),
                setsContainer.size());
    }

    /**
     * Interaction with the backward button.
     * <p>
     * Changes {@link SetsContainer} in {@link #setsContainerMap}.
     * Also moves the index one element backward.
     *
     * @param interactionId a unique id to interact with discord. It's also the key
     *                      to {@link #setsContainerMap}
     * @return returns a response with the index already moved
     * @throws EmptySetsContainerException if the container is empty
     */
    public CommandSearchResponse arrowBackward(String interactionId) {

        SetsContainer setsContainer = this.setsContainerMap.get(interactionId);
        if (setsContainer == null)
            throw new EmptySetsContainerException("Sets Container is null");
        setsContainer.prev();
        Set set = setsContainer.getCurrentSet();

        LOGGER.info("full_search_right: Message Hash Map size: {}", setsContainerMap.size());

        return new CommandSearchResponse(
                this.getButtonList(setsContainer),
                set,
                setsContainer.getCurrentIndex(),
                setsContainer.size());
    }

    /**
     * Jump to the start.
     * <p>
     * Changes {@link SetsContainer} in {@link #setsContainerMap}.
     * Also moves the index to the start.
     *
     * @param interactionId a unique id to interact with discord. It's also the key
     *                      to {@link #setsContainerMap}
     * @return returns a response with the index already moved
     * @throws EmptySetsContainerException if the container is empty
     */
    public CommandSearchResponse toStart(String interactionId) {

        SetsContainer setsContainer = this.setsContainerMap.get(interactionId);
        if (setsContainer == null)
            throw new EmptySetsContainerException("Sets Container is null");
        setsContainer.toStart();
        Set set = setsContainer.getCurrentSet();

        LOGGER.info("full_search_right: Message Hash Map size: {}", setsContainerMap.size());

        return new CommandSearchResponse(
                this.getButtonList(setsContainer),
                set,
                setsContainer.getCurrentIndex(),
                setsContainer.size());
    }

    /**
     * Jump to the end.
     * <p>
     * Changes {@link SetsContainer} in {@link #setsContainerMap}.
     * Also moves the index to the end.
     *
     * @param interactionId a unique id to interact with discord. It's also the key
     *                      to {@link #setsContainerMap}
     * @return returns a response with the index already moved
     * @throws EmptySetsContainerException if the container is empty
     */
    public CommandSearchResponse toEnd(String interactionId) {

        SetsContainer setsContainer = this.setsContainerMap.get(interactionId);
        if (setsContainer == null)
            throw new EmptySetsContainerException("Sets Container is null");
        setsContainer.toEnd();
        Set set = setsContainer.getCurrentSet();

        LOGGER.info("full_search_right: Message Hash Map size: {}", setsContainerMap.size());

        return new CommandSearchResponse(
                this.getButtonList(setsContainer),
                set,
                setsContainer.getCurrentIndex(),
                setsContainer.size());
    }

    /**
     * Interacting with the page button.
     * The page button allows the user to select the desired page.
     * <p>
     * Changes {@link SetsContainer} in {@link #setsContainerMap}.
     * Also moves the index depending on the page.
     *
     * @param interactionId a unique id to interact with discord. It's also the key
     *                      to {@link #setsContainerMap}
     * @return returns a response with the index already moved
     * @throws EmptySetsContainerException if the container is empty
     * @throws IndexOutOfBoundsException   if the index is out of bounds
     */
    public CommandSearchResponse pageButtonInteraction(String interactionId, int page) {

        SetsContainer setsContainer = this.setsContainerMap.get(interactionId);
        if (setsContainer == null)
            throw new EmptySetsContainerException("Sets Container is null");
        setsContainer.setCurrentIndex(page);
        Set set = setsContainer.getCurrentSet();

        LOGGER.info("full_search_right: Message Hash Map size: {}", setsContainerMap.size());

        return new CommandSearchResponse(
                this.getButtonList(setsContainer),
                set,
                setsContainer.getCurrentIndex(),
                setsContainer.size());
    }

}
