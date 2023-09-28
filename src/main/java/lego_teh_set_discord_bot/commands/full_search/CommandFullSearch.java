package lego_teh_set_discord_bot.commands.full_search;

import lego_teh_set_discord_bot.component_creators.EmbedBuilderCreator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.interactions.MessageEditCallbackAction;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import rebrickableAPI.OrderingType;
import rebrickableAPI.RebrickableAPIGetter;
import rebrickableAPI.returned_objects.Set;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandFullSearch extends ListenerAdapter {

    private HashMap<String, RequestMessage> messageHashMap = new HashMap<String, RequestMessage>();
    private Button firstButton = Button.secondary("full_search_first", Emoji.fromFormatted("<:rewind_lts:1156918918103965716>"));
    private Button lastButton = Button.secondary("full_search_last", Emoji.fromFormatted("<:fast_forward_lts:1156830249250725948>"));
    private String arrowBackwardEmoji = "<:arrow_backward_lts:1156919033497657345>";
    private String arrowForwardEmoji = "<:arrow_forward_lts:1156918988572475455>";

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if(event.getName().equals("full_search")) {
            OptionMapping optionMapping = event.getOption("request");
            String search = optionMapping.getAsString();

            OptionMapping orderingTypeOptionMapping = event.getOption("ordering");

            List<Set> resultSearch;

            try {
                String orderingType = orderingTypeOptionMapping.getAsString();
                resultSearch = new RebrickableAPIGetter().getSearchResult(search, OrderingType.getOrderingTypeFromString(orderingType));
            }
            catch (NullPointerException e) {
                resultSearch = new RebrickableAPIGetter().getSearchResult(search);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if(resultSearch.isEmpty()){
                event.replyEmbeds(EmbedBuilderCreator.Companion.getNullErrorEmbed().build()).setEphemeral(true).queue();
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
                replyCallbackAction.addActionRow(
                        firstButton.asDisabled(),
                        Button.secondary("full_search_left", Emoji.fromFormatted(arrowBackwardEmoji)).asDisabled(),
                        Button.secondary("full_search_right", Emoji.fromFormatted(arrowForwardEmoji)),
                        lastButton,
                        Button.link(requestMessage.getCurrentSet().getSetUrl(), "Set on Rebrickable")
                );
            }
            else{
                replyCallbackAction.addActionRow(
                        Button.link(requestMessage.getCurrentSet().getSetUrl(), "Set on Rebrickable")
                );
            }
            replyCallbackAction.queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {

        if(event.getComponentId().equals("full_search_right")) {
            RequestMessage requestMessage = this.messageHashMap.get(event.getMessage().getInteraction().getId());

            if(requestMessage.hasNext())
                requestMessage.next();

            MessageEditCallbackAction replyCallbackAction = event.editMessageEmbeds(requestMessage.getCurrentEmbedBuilderWithPageNumber().build());

            Button buttonLeft = Button.secondary("full_search_left", Emoji.fromFormatted(arrowBackwardEmoji));
            Button buttonRight = Button.secondary("full_search_right", Emoji.fromFormatted(arrowForwardEmoji));
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
            actionRow.add(Button.link(requestMessage.getCurrentSet().getSetUrl(), "Set on Rebrickable"));

            replyCallbackAction.setActionRow(actionRow).queue();
        }

        if(event.getComponentId().equals("full_search_left")) {
            RequestMessage requestMessage = this.messageHashMap.get(event.getMessage().getInteraction().getId());

            if(requestMessage.hasPrev())
                requestMessage.prev();

            MessageEditCallbackAction replyCallbackAction = event.editMessageEmbeds(requestMessage.getCurrentEmbedBuilderWithPageNumber().build());

            Button buttonLeft = Button.secondary("full_search_left", Emoji.fromFormatted(arrowBackwardEmoji));
            Button buttonRight = Button.secondary("full_search_right", Emoji.fromFormatted(arrowForwardEmoji));
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

            actionRow.add(Button.link(requestMessage.getCurrentSet().getSetUrl(), "Set on Rebrickable"));
            replyCallbackAction.setActionRow(actionRow).queue();
        }

        if(event.getComponentId().equals("full_search_first")) {
            RequestMessage requestMessage = this.messageHashMap.get(event.getMessage().getInteraction().getId());
            EmbedBuilder answer = requestMessage.getFirstEmbedBuilder();

            Button buttonLeft = Button.secondary("full_search_left", Emoji.fromFormatted(arrowBackwardEmoji)).asDisabled();
            Button buttonRight = Button.secondary("full_search_right", Emoji.fromFormatted(arrowForwardEmoji));

            event.editMessageEmbeds(answer.build()).setActionRow(
                    firstButton.asDisabled(), buttonLeft, buttonRight, lastButton,
                    Button.link(requestMessage.getCurrentSet().getSetUrl(), "Set on Rebrickable")
            ).queue();
        }

        if(event.getComponentId().equals("full_search_last")) {
            RequestMessage requestMessage = this.messageHashMap.get(event.getMessage().getInteraction().getId());
            EmbedBuilder answer = requestMessage.getLastEmbedBuilder();

            Button buttonLeft = Button.secondary("full_search_left", Emoji.fromFormatted(arrowBackwardEmoji));
            Button buttonRight = Button.secondary("full_search_right", Emoji.fromFormatted(arrowForwardEmoji)).asDisabled();

            event.editMessageEmbeds(answer.build()).setActionRow(
                    firstButton, buttonLeft, buttonRight, lastButton.asDisabled(),
                    Button.link(requestMessage.getCurrentSet().getSetUrl(), "Set on Rebrickable")
            ).queue();
        }
    }
}
