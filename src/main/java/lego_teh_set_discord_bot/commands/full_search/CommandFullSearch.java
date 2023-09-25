package lego_teh_set_discord_bot.commands.full_search;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.interactions.MessageEditCallbackAction;
import rebrickableAPI.RebrickableAPIGetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandFullSearch extends ListenerAdapter {

    private HashMap<String, RequestMessage> messageHashMap = new HashMap<String, RequestMessage>();
    private Button firstButton = Button.secondary("full_search_first", Emoji.fromFormatted("⏮"));
    private Button lastButton = Button.secondary("full_search_last", Emoji.fromFormatted("⏭"));

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if(event.getName().equals("full_search")) {
            OptionMapping optionMapping = event.getOption("request");
            String search = optionMapping.getAsString();

            RequestMessage requestMessage = new RequestMessage(new RebrickableAPIGetter().getSearchResult(search));

            this.messageHashMap.put(event.getInteraction().getId(), requestMessage);
            EmbedBuilder embedBuilder = requestMessage.getCurrentEmbedBuilder();
            event.replyEmbeds(embedBuilder.build())
                    .addActionRow(
                            firstButton.asDisabled(),
                            Button.secondary("full_search_left", Emoji.fromFormatted("⬅️")).asDisabled(),
                            Button.secondary("full_search_right", Emoji.fromFormatted("➡️")),
                            lastButton
                    ).queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {

        if(event.getComponentId().equals("full_search_right")) {
            RequestMessage requestMessage = this.messageHashMap.get(event.getMessage().getInteraction().getId());

            if(requestMessage.hasNext())
                requestMessage.next();

            MessageEditCallbackAction replyCallbackAction = event.editMessageEmbeds(requestMessage.getCurrentEmbedBuilder().build());

            Button buttonLeft = Button.secondary("full_search_left", Emoji.fromFormatted("⬅️"));
            Button buttonRight = Button.secondary("full_search_right", Emoji.fromFormatted("➡️"));
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

            replyCallbackAction.setActionRow(actionRow).queue();
        }

        if(event.getComponentId().equals("full_search_left")) {
            RequestMessage requestMessage = this.messageHashMap.get(event.getMessage().getInteraction().getId());

            if(requestMessage.hasPrev())
                requestMessage.prev();

            MessageEditCallbackAction replyCallbackAction = event.editMessageEmbeds(requestMessage.getCurrentEmbedBuilder().build());

            Button buttonLeft = Button.secondary("full_search_left", Emoji.fromFormatted("⬅️"));
            Button buttonRight = Button.secondary("full_search_right", Emoji.fromFormatted("➡️"));
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

            replyCallbackAction.setActionRow(actionRow).queue();
            System.out.println(this.messageHashMap.size());
        }

        if(event.getComponentId().equals("full_search_first")) {
            RequestMessage requestMessage = this.messageHashMap.get(event.getMessage().getInteraction().getId());
            EmbedBuilder answer = requestMessage.getFirstEmbedBuilder();

            Button buttonLeft = Button.secondary("full_search_left", Emoji.fromFormatted("⬅️")).asDisabled();
            Button buttonRight = Button.secondary("full_search_right", Emoji.fromFormatted("➡️"));

            event.editMessageEmbeds(answer.build()).setActionRow(
                    firstButton.asDisabled(), buttonLeft, buttonRight, lastButton
            ).queue();
        }

        if(event.getComponentId().equals("full_search_last")) {
            RequestMessage requestMessage = this.messageHashMap.get(event.getMessage().getInteraction().getId());
            EmbedBuilder answer = requestMessage.getLastEmbedBuilder();

            Button buttonLeft = Button.secondary("full_search_left", Emoji.fromFormatted("⬅️"));
            Button buttonRight = Button.secondary("full_search_right", Emoji.fromFormatted("➡️")).asDisabled();

            event.editMessageEmbeds(answer.build()).setActionRow(
                    firstButton, buttonLeft, buttonRight, lastButton.asDisabled()
            ).queue();
        }
    }
}
