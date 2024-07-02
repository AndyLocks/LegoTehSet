package org.lts.lego_teh_set.lego_teh_set_discord_bot.commands.search;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lts.lego_teh_set.command.search.CommandSearchResponse;
import org.lts.lego_teh_set.command.search.EmptySetsContainerException;
import org.lts.lego_teh_set.command.search.SearchCommandHandler;
import org.lts.lego_teh_set.rebrickableAPI.returned_objects.Set;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchCommandHandlerTest {

    private final String globalInteractionId = "test_1";
    private final String globalSearch = "dodge";

    private final Button firstButton = Button.secondary("search_first", Emoji.fromFormatted("<:rewind_lts:1156918918103965716>"));
    private final Button lastButton = Button.secondary("search_last", Emoji.fromFormatted("<:fast_forward_lts:1156830249250725948>"));
    private final Button arrowBackwardButton = Button.secondary("search_arrow_backward", Emoji.fromFormatted("<:arrow_backward_lts:1156919033497657345>"));
    private final Button arrowForwardButton = Button.secondary("search_arrow_forward", Emoji.fromFormatted("<:arrow_forward_lts:1156918988572475455>"));
    private final Button pageButton = Button.secondary("search_text_input_button", "page");

    private SearchCommandHandler searchCommandHandler;

    @Before
    public void setSearchCommandHandler_init() {
        this.searchCommandHandler = new SearchCommandHandler();
        searchCommandHandler.init(
                globalSearch,
                null,
                globalInteractionId
        );
    }

    @Test
    public void init_test() {
        Set set = new Set(
                "42111-1",
                "Dom's Dodge Charger",
                2020,
                1,
                1077,
                "https://cdn.rebrickable.com/media/sets/42111-1/56361.jpg",
                "https://rebrickable.com/sets/42111-1/doms-dodge-charger/",
                "2023-04-23T10:54:36.190875Z"
        );
        CommandSearchResponse commandSearchResponse = new CommandSearchResponse(Arrays.asList(
                firstButton.asDisabled(),
                arrowBackwardButton.asDisabled(),
                arrowForwardButton,
                lastButton,
                pageButton
        ),
                set, 0, 4
        );

        CommandSearchResponse initCommandSearchResponse =  searchCommandHandler.init("dodge", null, this.globalInteractionId);
        assertEquals(commandSearchResponse, initCommandSearchResponse);
    }

    @Test
    public void forward_button_test() {
        Set set = new Set(
                "75893-1",
                "2018 Dodge Challenger SRT Demon and 1970 Dodge Charger R/T",
                2019,
                601,
                485,
                "https://cdn.rebrickable.com/media/sets/75893-1/15770.jpg",
                "https://rebrickable.com/sets/75893-1/2018-dodge-challenger-srt-demon-and-1970-dodge-charger-rt/",
                "2024-01-13T21:32:29.557004Z"
        );

        CommandSearchResponse commandSearchResponse = new CommandSearchResponse(Arrays.asList(
                firstButton,
                arrowBackwardButton,
                arrowForwardButton,
                lastButton,
                pageButton
        ),
                set, 1, 4
        );

        CommandSearchResponse forwardButtonCommandSearchResponse = searchCommandHandler.arrowForward(
                globalInteractionId
        );
        assertEquals(commandSearchResponse, forwardButtonCommandSearchResponse);
    }

    @Test
    public void backward_button_test() {
        searchCommandHandler.arrowForward(this.globalInteractionId);
        searchCommandHandler.arrowForward(this.globalInteractionId);
        searchCommandHandler.arrowForward(this.globalInteractionId);

        Set set = new Set(
                "76904-1",
                "Mopar Dodge//SRT Top Fuel Dragster and 1970 Dodge Challenger T/A",
                2021,
                601,
                627,
                "https://cdn.rebrickable.com/media/sets/76904-1/88304.jpg",
                "https://rebrickable.com/sets/76904-1/mopar-dodgesrt-top-fuel-dragster-and-1970-dodge-challenger-ta/",
                "2021-05-18T04:22:54.460739Z"
        );

        CommandSearchResponse commandSearchResponse = new CommandSearchResponse(Arrays.asList(
                firstButton,
                arrowBackwardButton,
                arrowForwardButton,
                lastButton,
                pageButton
        ),
                set, 2, 4
        );
        CommandSearchResponse backwardButtonCommandSearchResponse = searchCommandHandler.arrowBackward(
                globalInteractionId
        );
        assertEquals(commandSearchResponse, backwardButtonCommandSearchResponse);
    }

    @Test
    public void to_end_button_test() {
        Set set = new Set(
                "76912-1",
                "Fast & Furious 1970 Dodge Charger R/T",
                2022,
                601,
                345,
                "https://cdn.rebrickable.com/media/sets/76912-1/104445.jpg",
                "https://rebrickable.com/sets/76912-1/fast-furious-1970-dodge-charger-rt/",
                "2022-06-15T08:14:09.778281Z"
        );

        CommandSearchResponse commandSearchResponse = new CommandSearchResponse(Arrays.asList(
                firstButton,
                arrowBackwardButton,
                arrowForwardButton.asDisabled(),
                lastButton.asDisabled(),
                pageButton
        ),
                set, 3, 4
        );

        CommandSearchResponse backwardButtonCommandSearchResponse = searchCommandHandler.toEnd(
                globalInteractionId
        );
        assertEquals(commandSearchResponse, backwardButtonCommandSearchResponse);
    }

    @Test
    public void to_start_button() {
        searchCommandHandler.toEnd(this.globalInteractionId);
        Set set = new Set(
                "42111-1",
                "Dom's Dodge Charger",
                2020,
                1,
                1077,
                "https://cdn.rebrickable.com/media/sets/42111-1/56361.jpg",
                "https://rebrickable.com/sets/42111-1/doms-dodge-charger/",
                "2023-04-23T10:54:36.190875Z"
        );

        CommandSearchResponse commandSearchResponse = new CommandSearchResponse(Arrays.asList(
                firstButton.asDisabled(),
                arrowBackwardButton.asDisabled(),
                arrowForwardButton,
                lastButton,
                pageButton
        ),
                set, 0, 4
        );

        CommandSearchResponse backwardButtonCommandSearchResponse = searchCommandHandler.toStart(
                globalInteractionId
        );
        assertEquals(commandSearchResponse, backwardButtonCommandSearchResponse);
    }

    @Test(expected = EmptySetsContainerException.class)
    public void init_emptySetsContainerException_test() {
        SearchCommandHandler searchCommandHandler1 = new SearchCommandHandler();
        searchCommandHandler1.init(
                "aboba",
                null,
                "aboba"
        );
    }

    @Test(expected = EmptySetsContainerException.class)
    public void toEnd_EmptySetsContainerException_test() {
        SearchCommandHandler searchCommandHandler1 = new SearchCommandHandler();
        searchCommandHandler1.toEnd(
                "aboba"
        );
    }
    @Test(expected = EmptySetsContainerException.class)
    public void toEnd_EmptySetsContainerException_test_with_init() {
        SearchCommandHandler searchCommandHandler1 = new SearchCommandHandler();
        searchCommandHandler1.init("aboba", null, "aboba");
        searchCommandHandler1.toEnd(
                "aboba"
        );
    }

    @Test(expected = EmptySetsContainerException.class)
    public void toStart_EmptySetsContainerException_test() {
        SearchCommandHandler searchCommandHandler1 = new SearchCommandHandler();
        searchCommandHandler1.toStart(
                "aboba"
        );
    }

    @Test(expected = EmptySetsContainerException.class)
    public void toStart_EmptySetsContainerException_test_with_init() {
        SearchCommandHandler searchCommandHandler1 = new SearchCommandHandler();
        searchCommandHandler1.init("aboba", null, "aboba");
        searchCommandHandler1.toStart(
                "aboba"
        );
    }

    @Test(expected = EmptySetsContainerException.class)
    public void page_button_interaction_EmptySetsContainerException_test() {
        SearchCommandHandler searchCommandHandler1 = new SearchCommandHandler();
        searchCommandHandler1.pageButtonInteraction(
                "aboba",
                3
        );
    }

    @Test(expected = EmptySetsContainerException.class)
    public void page_button_interaction_EmptySetsContainerException_test_with_init() {
        SearchCommandHandler searchCommandHandler1 = new SearchCommandHandler();
        searchCommandHandler1.init("aboba", null, "aboba");
        searchCommandHandler1.pageButtonInteraction(
                "aboba",
                3
        );
    }

    @Test
    public void page_button_interaction_test() {
        Set set = new Set(
                "76904-1",
                "Mopar Dodge//SRT Top Fuel Dragster and 1970 Dodge Challenger T/A",
                2021,
                601,
                627,
                "https://cdn.rebrickable.com/media/sets/76904-1/88304.jpg",
                "https://rebrickable.com/sets/76904-1/mopar-dodgesrt-top-fuel-dragster-and-1970-dodge-challenger-ta/",
                "2021-05-18T04:22:54.460739Z"
        );
        CommandSearchResponse commandSearchResponse = new CommandSearchResponse(Arrays.asList(
                firstButton,
                arrowBackwardButton,
                arrowForwardButton,
                lastButton,
                pageButton
        ),
                set, 2, 4
        );

        CommandSearchResponse backwardButtonCommandSearchResponse = searchCommandHandler.pageButtonInteraction(
                globalInteractionId,
                2
        );
        assertEquals(commandSearchResponse, backwardButtonCommandSearchResponse);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void page_less_than_zero() {
        searchCommandHandler.pageButtonInteraction(this.globalInteractionId, -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void page_is_larger_than_maximum() {
        searchCommandHandler.pageButtonInteraction(this.globalInteractionId, 1000);
    }
}