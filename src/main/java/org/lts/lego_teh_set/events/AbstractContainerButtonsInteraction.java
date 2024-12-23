package org.lts.lego_teh_set.events;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.lts.lego_teh_set.commands.Buttons;
import org.lts.lego_teh_set.commands.TextInputs;
import org.lts.lego_teh_set.commands.container.AbstractContainer;
import org.lts.lego_teh_set.commands.container.ContainerEmbed;
import org.lts.lego_teh_set.config.redis.AbstractContainerTemplateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/// Interacts with all {@link AbstractContainer} in the application when the user interacts with the following buttons:
///
/// - {@link Buttons#PAGE}
/// - {@link Buttons#TO_START}
/// - {@link Buttons#FORWARD}
/// - {@link Buttons#BACKWARD}
/// - {@link Buttons#TO_END}
///
/// ---
///
/// During interaction, it gets containers from Redis by interaction id (special id in Discord API).
/// Later works with the container by modifying it and reading data and writes it back to redis.
@Component
public class AbstractContainerButtonsInteraction extends ListenerAdapter {

    private final Logger LOG = LoggerFactory.getLogger(AbstractContainerButtonsInteraction.class);
    private final RedisTemplate<String, AbstractContainer> abstractContainerRedisTemplate;

    @Autowired
    public AbstractContainerButtonsInteraction(RedisTemplate<String, AbstractContainer> abstractContainerRedisTemplate) {
        this.abstractContainerRedisTemplate = abstractContainerRedisTemplate;
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {

        if (Buttons.fromButton(event.getButton()) == Buttons.PAGE) {
            var fullSearchTextInput = TextInput.create(TextInputs.PAGE.getId(), TextInputs.PAGE.getLabel(), TextInputStyle.SHORT)
                    .setPlaceholder("Page")
                    .setMinLength(1)
                    .setRequired(true)
                    .setMaxLength(3)
                    .build();

            var modal = Modal.create("search_text_input_modmail", "Modmail")
                    .addComponents(ActionRow.of(fullSearchTextInput))
                    .build();

            event.replyModal(modal).queue();
            return;
        }

        var key = AbstractContainerTemplateConfig.ABSTRACT_CONTAINER_KEY_GENERATOR.apply(event.getMessage().getInteraction().getId());
        var abstractContainer = abstractContainerRedisTemplate.opsForValue().get(key);

        if (abstractContainer == null) {
            LOG.debug("Null abstract container with key [{}]", key);
            event.reply("This message is old. I cant interact with it.")
                    .setEphemeral(true)
                    .queue();

            return;
        }

        switch (Buttons.fromButton(event.getButton())) {
            case TO_START -> abstractContainer.toStart();
            case FORWARD -> abstractContainer.next();
            case BACKWARD -> abstractContainer.previous();
            case TO_END -> abstractContainer.toEnd();
            case null, default -> {
                LOG.error("Unknown button with name [{}]", event.getComponentId());
                return;
            }
        }

        event.editMessageEmbeds(ContainerEmbed.toEmbedBuilder(abstractContainer.currentEmbed()).build())
                .setActionRow(abstractContainer.buttons())
                .queue(m -> {
                    abstractContainerRedisTemplate.opsForValue()
                            .set(key, abstractContainer, 5, TimeUnit.MINUTES);
                });
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {

        var key = AbstractContainerTemplateConfig.ABSTRACT_CONTAINER_KEY_GENERATOR.apply(event.getMessage().getInteraction().getId());

        int pageNumber;
        try {
            pageNumber = Integer.parseInt(event.getValue(TextInputs.PAGE.getId()).getAsString().trim());
        } catch (NumberFormatException _) {
            event.reply("Incorrect input. The input must be a number.").setEphemeral(true).queue();
            return;
        } catch (NullPointerException e) {
            LOG.error("[ModalInteractionEvent] -> the text input value is null: {}", e.getMessage());
            event.reply("Incorrect input.").setEphemeral(true).queue();
            return;
        }

        var container = abstractContainerRedisTemplate.opsForValue().get(key);

        if (container == null) {
            LOG.debug("Null abstract container with key [{}]", key);
            event.reply("This message is old. I cant interact with it.")
                    .setEphemeral(true)
                    .queue();

            return;
        }

        try {
            container.setPage(pageNumber - 1);
        } catch (IllegalArgumentException e) {
            event.reply(e.getMessage())
                    .setEphemeral(true)
                    .queue();

            return;
        }

        event.editMessageEmbeds(ContainerEmbed.toEmbedBuilder(container.currentEmbed()).build())
                .setActionRow(container.buttons())
                .queue(m -> {
                    abstractContainerRedisTemplate.opsForValue()
                            .set(key, container, AbstractContainerTemplateConfig.TTL_DURATION);
                });
    }

}
