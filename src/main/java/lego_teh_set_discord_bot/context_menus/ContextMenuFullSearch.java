package lego_teh_set_discord_bot.context_menus;

import lego_teh_set_discord_bot.commands.full_search.RequestMessage;
import lego_teh_set_discord_bot.component_creators.EmbedBuilderCreator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.requests.restaction.interactions.MessageEditCallbackAction;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import rebrickableAPI.RebrickableAPIGetter;
import rebrickableAPI.returned_objects.Set;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.*;

public class ContextMenuFullSearch extends ListenerAdapter {

    private static final Logger LOGGER = Logger.getLogger(ContextMenuFullSearch.class.getName());

    private HashMap<String, RequestMessage> messageHashMap = new HashMap<String, RequestMessage>();
    private Button firstButton = Button.secondary("full_search_first_context_menu", Emoji.fromFormatted("<:rewind_lts:1156918918103965716>"));
    private Button lastButton = Button.secondary("full_search_last_context_menu", Emoji.fromFormatted("<:fast_forward_lts:1156830249250725948>"));
    private String arrowBackwardEmoji = "<:arrow_backward_lts:1156919033497657345>";
    private String arrowForwardEmoji = "<:arrow_forward_lts:1156918988572475455>";

    public ContextMenuFullSearch() throws IOException {
        LOGGER.setLevel(Level.FINE);
        FileHandler fileHandler = new FileHandler("/home/illia/IdeaProjects/LegoTehSet/full_search_context_menu.log");
        fileHandler.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(fileHandler);
        LOGGER.addHandler(new ConsoleHandler());
    }

    @Override
    public void onMessageContextInteraction(MessageContextInteractionEvent event) {
        if(event.getName().equals("full_search")) {
            LOGGER.log(Level.FINE, "[START] ID: {0}", event.getInteraction().getId());
            String search = event.getTarget().getContentDisplay();
            LOGGER.log(Level.FINE, "search: {0}", search);

            List<Set> resultSearch = new RebrickableAPIGetter().getSearchResult(search);

            if(resultSearch.isEmpty()){
                event.replyEmbeds(EmbedBuilderCreator.Companion.getNullErrorEmbed().build()).setEphemeral(true).queue();
                LOGGER.log(Level.FINE, "Result search is null");
            }

            RequestMessage requestMessage = new RequestMessage(resultSearch);

            this.messageHashMap.put(event.getInteraction().getId(), requestMessage);
            EmbedBuilder embedBuilder;
            if(!requestMessage.hasNext())
                embedBuilder = requestMessage.getCurrentEmbedBuilder();
            else
                embedBuilder = requestMessage.getCurrentEmbedBuilderWithPageNumber();

            ReplyCallbackAction replyCallbackAction = event.replyEmbeds(embedBuilder.build());
            if(requestMessage.hasNext()){
                LOGGER.log(Level.FINE, "Request message has a next set");
                Button button = Button.secondary("full_search_text_input_button_context_menu", "page");
                replyCallbackAction.addActionRow(
                        firstButton.asDisabled(),
                        Button.secondary("full_search_left_context_menu", Emoji.fromFormatted(arrowBackwardEmoji)).asDisabled(),
                        Button.secondary("full_search_right_context_menu", Emoji.fromFormatted(arrowForwardEmoji)),
                        lastButton,
                        button
                );
            }
            else{
                replyCallbackAction.addActionRow(
                        Button.link(requestMessage.getCurrentSet().getSetUrl(), "Set on Rebrickable")
                );
            }
            replyCallbackAction.queue();
            LOGGER.log(Level.FINE, "Message Hash Map size: {0}", messageHashMap.size());
        }
    }
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {

        if(event.getComponentId().equals("full_search_right_context_menu")) {
            RequestMessage requestMessage = this.messageHashMap.get(event.getMessage().getInteraction().getId());

            if(requestMessage.hasNext())
                requestMessage.next();

            MessageEditCallbackAction replyCallbackAction = event.editMessageEmbeds(requestMessage.getCurrentEmbedBuilderWithPageNumber().build());

            Button buttonLeft = Button.secondary("full_search_left_context_menu", Emoji.fromFormatted(arrowBackwardEmoji));
            Button buttonRight = Button.secondary("full_search_right_context_menu", Emoji.fromFormatted(arrowForwardEmoji));
            if(!requestMessage.hasPrev())
                buttonLeft = buttonLeft.asDisabled();

            if(!requestMessage.hasNext())
                buttonRight = buttonRight.asDisabled();

            List<Button> actionRow = new ArrayList<>();
            if(!requestMessage.hasPrev()){
                actionRow.add(firstButton.asDisabled());
            }

            else {
                actionRow.add(firstButton);
            }

            actionRow.add(buttonLeft);
            actionRow.add(buttonRight);
            if(!requestMessage.hasNext()) {
                actionRow.add(lastButton.asDisabled());
            }
            else {
                actionRow.add(lastButton);
            }

            Button button = Button.secondary("full_search_text_input_button_context_menu", "Page");
            actionRow.add(button);

            replyCallbackAction.setActionRow(actionRow).queue();
            LOGGER.log(Level.FINE, "full_search_right_context_menu: Message Hash Map size: {0}", messageHashMap.size());
        }

        if(event.getComponentId().equals("full_search_left_context_menu")) {
            RequestMessage requestMessage = this.messageHashMap.get(event.getMessage().getInteraction().getId());

            if(requestMessage.hasPrev())
                requestMessage.prev();

            MessageEditCallbackAction replyCallbackAction = event.editMessageEmbeds(requestMessage.getCurrentEmbedBuilderWithPageNumber().build());

            Button buttonLeft = Button.secondary("full_search_left_context_menu", Emoji.fromFormatted(arrowBackwardEmoji));
            Button buttonRight = Button.secondary("full_search_right_context_menu", Emoji.fromFormatted(arrowForwardEmoji));
            if(!requestMessage.hasPrev())
                buttonLeft = buttonLeft.asDisabled();

            if(!requestMessage.hasNext())
                buttonRight = buttonRight.asDisabled();

            List<Button> actionRow = new ArrayList<>();
            if(!requestMessage.hasPrev()){
                actionRow.add(firstButton.asDisabled());
            }

            else {
                actionRow.add(firstButton);
            }

            actionRow.add(buttonLeft);
            actionRow.add(buttonRight);
            if(!requestMessage.hasNext()) {
                actionRow.add(lastButton.asDisabled());
            }
            else {
                actionRow.add(lastButton);
            }

            Button button = Button.secondary("full_search_text_input_button_context_menu", "Page");
            actionRow.add(button);

            replyCallbackAction.setActionRow(actionRow).queue();
            LOGGER.log(Level.FINE, "full_search_left_context_menu: Message Hash Map size: {0}", messageHashMap.size());
        }

        if(event.getComponentId().equals("full_search_first_context_menu")) {
            RequestMessage requestMessage = this.messageHashMap.get(event.getMessage().getInteraction().getId());
            EmbedBuilder answer = requestMessage.getFirstEmbedBuilder();

            Button buttonLeft = Button.secondary("full_search_left_context_menu", Emoji.fromFormatted(arrowBackwardEmoji)).asDisabled();
            Button buttonRight = Button.secondary("full_search_right_context_menu", Emoji.fromFormatted(arrowForwardEmoji));

            Button button = Button.secondary("full_search_text_input_button_context_menu", "Page");

            event.editMessageEmbeds(answer.build()).setActionRow(
                    firstButton.asDisabled(), buttonLeft, buttonRight, lastButton,
                    button
            ).queue();
            LOGGER.log(Level.FINE, "full_search_first_context_menu: Message Hash Map size: {0}", messageHashMap.size());
        }

        if(event.getComponentId().equals("full_search_last_context_menu")) {
            RequestMessage requestMessage = this.messageHashMap.get(event.getMessage().getInteraction().getId());
            EmbedBuilder answer = requestMessage.getLastEmbedBuilder();

            Button buttonLeft = Button.secondary("full_search_left_context_menu", Emoji.fromFormatted(arrowBackwardEmoji));
            Button buttonRight = Button.secondary("full_search_right_context_menu", Emoji.fromFormatted(arrowForwardEmoji)).asDisabled();

            Button button = Button.secondary("full_search_text_input_button_context_menu", "Page");

            event.editMessageEmbeds(answer.build()).setActionRow(
                    firstButton, buttonLeft, buttonRight, lastButton.asDisabled(),
                    button
            ).queue();
            LOGGER.log(Level.FINE, "full_search_last_context_menu: Message Hash Map size: {0}", messageHashMap.size());
        }

        if(event.getComponentId().equals("full_search_text_input_button_context_menu")) {
            TextInput fullSearchTextInput = TextInput.create("full_search_text_input_context_menu", "Page", TextInputStyle.SHORT)
                    .setPlaceholder("Page")
                    .setMinLength(1)
                    .setMaxLength(4) // or setRequiredRange(10, 100)
                    .build();
            Modal modal = Modal.create("full_search_text_input_modmail_context_menu", "Modmail")
                    .addComponents(ActionRow.of(fullSearchTextInput))
                    .build();

            event.replyModal(modal).queue();
            LOGGER.log(Level.FINE, "full_search_text_input_button_context_menu: Message Hash Map size: {0}", messageHashMap.size());
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if(event.getModalId().equals("full_search_text_input_modmail_context_menu")) {
            String page = event.getValue("full_search_text_input_context_menu").getAsString();

            LOGGER.log(Level.FINE, "page: {0}", page);

            int pageNumber;
            try{
                pageNumber = Integer.parseInt(page);
            }
            catch (NumberFormatException e) {
                event.reply("Incorrect input").setEphemeral(true).queue();
                return;
            }
            if(pageNumber <= 0) {
                event.reply("The number must be greater than zero").setEphemeral(true).queue();
                return;
            }
            RequestMessage requestMessage = this.messageHashMap.get(event.getMessage().getInteraction().getId());
            if(pageNumber <= requestMessage.size()) {
                int index = pageNumber-1;
                requestMessage.setCurrentIndex(index);

                MessageEditCallbackAction messageEditCallbackAction = event.getInteraction().editMessageEmbeds(requestMessage.getCurrentEmbedBuilderWithPageNumber().build());

                List<Button> actionRowList = new ArrayList<>();
                if(index==0) {
                    actionRowList.add(firstButton.asDisabled());
                    actionRowList.add(Button.secondary("full_search_left_context_menu", Emoji.fromFormatted(arrowBackwardEmoji)).asDisabled());
                }
                else{
                    actionRowList.add(firstButton);
                    actionRowList.add(Button.secondary("full_search_left_context_menu", Emoji.fromFormatted(arrowBackwardEmoji)));
                }

                if(pageNumber== requestMessage.size()) {
                    actionRowList.add(Button.secondary("full_search_right_context_menu", Emoji.fromFormatted(arrowForwardEmoji)).asDisabled());
                    actionRowList.add(lastButton.asDisabled());
                }
                else{
                    actionRowList.add(Button.secondary("full_search_right_context_menu", Emoji.fromFormatted(arrowForwardEmoji)));
                    actionRowList.add(lastButton);
                }
                actionRowList.add(Button.secondary("full_search_text_input_button_context_menu", "Page"));

                messageEditCallbackAction.setActionRow(actionRowList).queue();
            }
            else {
                event.reply("the number must be from 0 to " + requestMessage.size()).setEphemeral(true).queue();
            }

            LOGGER.log(Level.FINE, "full_search_text_input_modmail_context_menu: Message Hash Map size: {0}", messageHashMap.size());
        }
    }
}
