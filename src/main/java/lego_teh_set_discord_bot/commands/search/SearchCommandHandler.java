package lego_teh_set_discord_bot.commands.search;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import rebrickableAPI.OrderingType;
import rebrickableAPI.RebrickableAPIGetter;
import rebrickableAPI.returned_objects.Set;
import spring_config.SpringConfig;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

/**
 * Search command handler.
 * <p>
 * This is where all the logic of the search command is hidden.
 * This class is used by the {@link CommandSearch} to get the {@link CommandSearchResponse}.
 */
public class SearchCommandHandler {
    private static final Logger LOGGER = Logger.getLogger(SearchCommandHandler.class.getName());
    private final ConcurrentMap<String, SetsContainer> setsContainerMap = new ConcurrentHashMap<>();
    private final Button firstButton = Button.secondary("search_first", Emoji.fromFormatted("<:rewind_lts:1156918918103965716>"));
    private final Button lastButton = Button.secondary("search_last", Emoji.fromFormatted("<:fast_forward_lts:1156830249250725948>"));
    private final Button arrowBackwardButton = Button.secondary("search_arrow_backward", Emoji.fromFormatted("<:arrow_backward_lts:1156919033497657345>"));
    private final Button arrowForwardButton = Button.secondary("search_arrow_forward", Emoji.fromFormatted("<:arrow_forward_lts:1156918988572475455>"));
    private final Button pageButton = Button.secondary("search_text_input_button", "page");

    /**
     * Get actual buttons.
     * <p>
     * The relevance of the buttons depends on the current index.
     *
     * @param setsContainer set container
     * @return returns the list of buttons depending on the current index
     *
     * @see SetsContainer
     */
    private List<Button> getButtonList(SetsContainer setsContainer) {
        List<Button> buttonList = new ArrayList<>();
        if(setsContainer.hasPrev()) {
            buttonList.add(firstButton);
            buttonList.add(arrowBackwardButton);
        }
        else {
            buttonList.add(firstButton.asDisabled());
            buttonList.add(arrowBackwardButton.asDisabled());
        }
        if(setsContainer.hasNext()) {
            buttonList.add(arrowForwardButton);
            buttonList.add(lastButton);
        }
        else {
            buttonList.add(arrowForwardButton.asDisabled());
            buttonList.add(lastButton.asDisabled());
        }
        buttonList.add(pageButton);

        LOGGER.log(Level.FINE, "full_search_right: Message Hash Map size: {0}", setsContainerMap.size());

        return buttonList;
    }

    public SearchCommandHandler() {
        LOGGER.setLevel(Level.FINE);
        try{
            FileHandler fileHandler = new FileHandler("/home/illia/IdeaProjects/LegoTehSet/search.log");
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Initialization of everything necessary for the search command to work.
     * <p>
     * Gets everything ready to go and adds {@link SetsContainer} to the {@link #setsContainerMap}.
     *
     * @param search user search request
     * @param orderingType the theme on which to sort the sets. Can also be null
     * @param interactionId a unique id to interact with discord. It's also the key to {@link #setsContainerMap}
     * @return returns a response
     */
    public CommandSearchResponse init(String search, OrderingType orderingType, String interactionId) {
        List<Set> resultSetList;
        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class)) {
            if(orderingType == null)
                resultSetList = context.getBean("rebrickableAPIGetter", RebrickableAPIGetter.class).getSearchResult(search);
            else
                resultSetList = context.getBean("rebrickableAPIGetter", RebrickableAPIGetter.class).getSearchResult(search, orderingType);
        }

        SetsContainer setsContainer = new SetsContainer(resultSetList);

        this.setsContainerMap.put(interactionId, setsContainer);

        LOGGER.log(Level.FINE, "full_search_right: Message Hash Map size: {0}", setsContainerMap.size());

        return new CommandSearchResponse(
                this.getButtonList(setsContainer),
                setsContainer.getCurrentSet(),
                setsContainer.getCurrentIndex(),
                setsContainer.size()
        );
    }

    /**
     * Interaction with the forward button.
     * <p>
     * Changes {@link SetsContainer} in {@link #setsContainerMap}.
     * Also moves the index one element forward.
     *
     * @param interactionId a unique id to interact with discord. It's also the key to {@link #setsContainerMap}
     * @return returns a response with the index already moved
     * @throws EmptySetsContainerException if the container is empty
     */
    public CommandSearchResponse arrowForward(String interactionId) {
        SetsContainer setsContainer = this.setsContainerMap.get(interactionId);
        if(setsContainer == null)
            throw new EmptySetsContainerException("Sets Container is null");
        setsContainer.next();
        Set set = setsContainer.getCurrentSet();

        LOGGER.log(Level.FINE, "full_search_right: Message Hash Map size: {0}", setsContainerMap.size());

        return new CommandSearchResponse(
                this.getButtonList(setsContainer),
                set,
                setsContainer.getCurrentIndex(),
                setsContainer.size()
        );
    }

    /**
     * Interaction with the backward button.
     * <p>
     * Changes {@link SetsContainer} in {@link #setsContainerMap}.
     * Also moves the index one element backward.
     *
     * @param interactionId a unique id to interact with discord. It's also the key to {@link #setsContainerMap}
     * @return returns a response with the index already moved
     * @throws EmptySetsContainerException if the container is empty
     */
    public CommandSearchResponse arrowBackward(String interactionId) {
        SetsContainer setsContainer = this.setsContainerMap.get(interactionId);
        if(setsContainer == null)
            throw new EmptySetsContainerException("Sets Container is null");
        setsContainer.prev();
        Set set = setsContainer.getCurrentSet();

        LOGGER.log(Level.FINE, "full_search_right: Message Hash Map size: {0}", setsContainerMap.size());

        return new CommandSearchResponse(
                this.getButtonList(setsContainer),
                set,
                setsContainer.getCurrentIndex(),
                setsContainer.size()
        );
    }

    /**
     * Jump to the start.
     * <p>
     * Changes {@link SetsContainer} in {@link #setsContainerMap}.
     * Also moves the index to the start.
     *
     * @param interactionId a unique id to interact with discord. It's also the key to {@link #setsContainerMap}
     * @return returns a response with the index already moved
     * @throws EmptySetsContainerException if the container is empty
     */
    public CommandSearchResponse toStart(String interactionId) {
        SetsContainer setsContainer = this.setsContainerMap.get(interactionId);
        if(setsContainer == null)
            throw new EmptySetsContainerException("Sets Container is null");
        setsContainer.toStart();
        Set set = setsContainer.getCurrentSet();

        LOGGER.log(Level.FINE, "full_search_right: Message Hash Map size: {0}", setsContainerMap.size());

        return new CommandSearchResponse(
                this.getButtonList(setsContainer),
                set,
                setsContainer.getCurrentIndex(),
                setsContainer.size()
        );
    }

    /**
     * Jump to the end.
     * <p>
     * Changes {@link SetsContainer} in {@link #setsContainerMap}.
     * Also moves the index to the end.
     *
     * @param interactionId a unique id to interact with discord. It's also the key to {@link #setsContainerMap}
     * @return returns a response with the index already moved
     * @throws EmptySetsContainerException if the container is empty
     */
    public CommandSearchResponse toEnd(String interactionId) {
        SetsContainer setsContainer = this.setsContainerMap.get(interactionId);
        if(setsContainer == null)
            throw new EmptySetsContainerException("Sets Container is null");
        setsContainer.toEnd();
        Set set = setsContainer.getCurrentSet();

        LOGGER.log(Level.FINE, "full_search_right: Message Hash Map size: {0}", setsContainerMap.size());

        return new CommandSearchResponse(
                this.getButtonList(setsContainer),
                set,
                setsContainer.getCurrentIndex(),
                setsContainer.size()
        );
    }

    /**
     * Interacting with the page button.
     * The page button allows the user to select the desired page.
     * <p>
     * Changes {@link SetsContainer} in {@link #setsContainerMap}.
     * Also moves the index depending on the page.
     *
     * @param interactionId a unique id to interact with discord. It's also the key to {@link #setsContainerMap}
     * @return returns a response with the index already moved
     * @throws EmptySetsContainerException if the container is empty
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public CommandSearchResponse pageButtonInteraction(String interactionId, int page) {
        SetsContainer setsContainer = this.setsContainerMap.get(interactionId);
        if(setsContainer == null)
            throw new EmptySetsContainerException("Sets Container is null");
        setsContainer.setCurrentIndex(page);
        Set set = setsContainer.getCurrentSet();

        LOGGER.log(Level.FINE, "full_search_right: Message Hash Map size: {0}", setsContainerMap.size());

        return new CommandSearchResponse(
                this.getButtonList(setsContainer),
                set,
                setsContainer.getCurrentIndex(),
                setsContainer.size()
        );
    }
}
