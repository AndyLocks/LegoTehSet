package lego_teh_set_discord_bot.commands.search;

import lego_teh_set_discord_bot.component_creators.EmbedBuilderCreator;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import rebrickableAPI.OrderingType;

/**
 * Класс для прослушки команды search.
 * Выдает в чат список всех найденных наборов по запросу.
 * <p>
 * Этот класс нужен для {@link net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder}
 * Этот класс реализует {@link #onSlashCommandInteraction(SlashCommandInteractionEvent)},
 * {@link #onButtonInteraction(ButtonInteractionEvent)}, {@link #onModalInteraction(ModalInteractionEvent)}
 */
public class CommandSearch extends ListenerAdapter {
    private final SearchCommandHandler searchCommandHandler = new SearchCommandHandler();

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("search")) {
            OptionMapping optionMapping = event.getOption("request");
            String search = optionMapping.getAsString();

            OptionMapping orderingTypeOptionMapping = event.getOption("ordering");
            OrderingType orderingType;
            try {
                orderingType = OrderingType.getOrderingTypeFromString(orderingTypeOptionMapping.getAsString());
            }
            catch (NullPointerException e) {
                orderingType = null;
            }
            CommandSearchResponse commandSearchResponse;
            try {
                commandSearchResponse = this.searchCommandHandler.init(
                        search,
                        orderingType,
                        event.getInteraction().getId()
                );
            }
            catch (EmptySetsContainerException e) {
                event.replyEmbeds(EmbedBuilderCreator.Companion.getNullErrorEmbed().build()).setEphemeral(true).queue();
                return;
            }

            event.replyEmbeds(
                    EmbedBuilderCreator.Companion.getEmbedBuilder(commandSearchResponse.getCurrentSet())
                            .setTitle(
                                    new StringBuilder().append(commandSearchResponse.getCurrentIndex()+1).append(" / ").append(commandSearchResponse.getMaxIndex()).toString()
                            ).build()
            ).addActionRow(commandSearchResponse.getButtonList()).queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if(event.getComponentId().equals("search_arrow_forward")) {
            String interactionId = event.getMessage().getInteraction().getId();
            CommandSearchResponse commandSearchResponse;
            try {
                commandSearchResponse = this.searchCommandHandler.arrowForward(interactionId);
            }
            catch (EmptySetsContainerException e){
                event.reply("I can't interact with this message, it's old").setEphemeral(true).queue();
                return;
            }
            event.editMessageEmbeds(
                    EmbedBuilderCreator.Companion.getEmbedBuilder(
                            commandSearchResponse.getCurrentSet()
                    )
                            .setTitle(
                                    new StringBuilder().append(commandSearchResponse.getCurrentIndex()+1).append(" / ").append(commandSearchResponse.getMaxIndex()).toString()
                            ).build()
            ).setActionRow(commandSearchResponse.getButtonList()).queue();
        }

        else if(event.getComponentId().equals("search_arrow_backward")) {
            String interactionId = event.getMessage().getInteraction().getId();
            CommandSearchResponse commandSearchResponse;
            try {
                commandSearchResponse = this.searchCommandHandler.arrowBackward(interactionId);
            }
            catch (EmptySetsContainerException e) {
                event.reply("I can't interact with this message, it's old").setEphemeral(true).queue();
                return;
            }
            event.editMessageEmbeds(
                    EmbedBuilderCreator.Companion.getEmbedBuilder(
                                    commandSearchResponse.getCurrentSet()
                            )
                            .setTitle(
                                    new StringBuilder().append(commandSearchResponse.getCurrentIndex()+1).append(" / ").append(commandSearchResponse.getMaxIndex()).toString()
                            ).build()
            ).setActionRow(commandSearchResponse.getButtonList()).queue();
        }

        else if(event.getComponentId().equals("search_first")) {
            String interactionId = event.getMessage().getInteraction().getId();
            CommandSearchResponse commandSearchResponse;
            try {
                commandSearchResponse = this.searchCommandHandler.toStart(interactionId);
            }
            catch (EmptySetsContainerException e){
                event.reply("I can't interact with this message, it's old").setEphemeral(true).queue();
                return;
            }
            event.editMessageEmbeds(
                    EmbedBuilderCreator.Companion.getEmbedBuilder(
                                    commandSearchResponse.getCurrentSet()
                            )
                            .setTitle(
                                    new StringBuilder().append(commandSearchResponse.getCurrentIndex()+1).append(" / ").append(commandSearchResponse.getMaxIndex()).toString()
                            ).build()
            ).setActionRow(commandSearchResponse.getButtonList()).queue();
        }

        else if(event.getComponentId().equals("search_last")) {
            String interactionId = event.getMessage().getInteraction().getId();
            CommandSearchResponse commandSearchResponse;
            try {
                commandSearchResponse = this.searchCommandHandler.toEnd(interactionId);
            }
            catch (EmptySetsContainerException e){
                event.reply("I can't interact with this message, it's old").setEphemeral(true).queue();
                return;
            }
            event.editMessageEmbeds(
                    EmbedBuilderCreator.Companion.getEmbedBuilder(
                                    commandSearchResponse.getCurrentSet()
                            )
                            .setTitle(
                                    new StringBuilder().append(commandSearchResponse.getCurrentIndex()+1).append(" / ").append(commandSearchResponse.getMaxIndex()).toString()
                            ).build()
            ).setActionRow(commandSearchResponse.getButtonList()).queue();
        }

        if(event.getComponentId().equals("search_text_input_button")) {
            TextInput fullSearchTextInput = TextInput.create("search_text_input", "Page", TextInputStyle.SHORT)
                    .setPlaceholder("Page")
                    .setMinLength(1)
                    .setMaxLength(4) // or setRequiredRange(10, 100)
                    .build();
            Modal modal = Modal.create("search_text_input_modmail", "Modmail")
                    .addComponents(ActionRow.of(fullSearchTextInput))
                    .build();

            event.replyModal(modal).queue();
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        String page = event.getValue("search_text_input").getAsString();

        int pageNumber;
        try{
            pageNumber = Integer.parseInt(page);
        }
        catch (NumberFormatException e) {
            event.reply("Incorrect input").setEphemeral(true).queue();
            return;
        }
        if(pageNumber <= 0) {
            event.reply("The number must be bigger than zero").setEphemeral(true).queue();
            return;
        }
        int index = pageNumber-1;
        CommandSearchResponse commandSearchResponse;
        try {
            commandSearchResponse = this.searchCommandHandler.pageButtonInteraction(event.getMessage().getInteraction().getId(), index);
        }
        catch (EmptySetsContainerException e) {
            event.reply("I can't interact with this message, it's old").setEphemeral(true).queue();
            return;
        }
        catch (IndexOutOfBoundsException e) {
            event.reply("Error").setEphemeral(true).queue();
            return;
        }
        event.getInteraction().editMessageEmbeds(
                EmbedBuilderCreator.Companion.getEmbedBuilder(
                                commandSearchResponse.getCurrentSet()
                        )
                        .setTitle(
                                new StringBuilder().append(commandSearchResponse.getCurrentIndex()+1).append(" / ").append(commandSearchResponse.getMaxIndex()).toString()
                        ).build()
        ).setActionRow(commandSearchResponse.getButtonList()).queue();
    }
}
