package org.lts.lego_teh_set.commands.implementations;

import io.grpc.StatusRuntimeException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.lts.Api;
import org.lts.ProfileHubGrpc;
import org.lts.lego_teh_set.commands.CommandDataSupplier;
import org.lts.lego_teh_set.commands.OptionDates;
import org.lts.lego_teh_set.commands.SlashCommands;
import org.lts.lego_teh_set.repository.SetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/// Class for listening to the command `add_favourite`.
///
/// Marks the set as a favourite
///
/// This class implements:
///
/// - [ListenerAdapter#onSlashCommandInteraction(SlashCommandInteractionEvent].
@Component
public class CommandAddFavourite extends ListenerAdapter implements CommandDataSupplier {

    private final ProfileHubGrpc.ProfileHubBlockingStub profileHubBlockingStub;
    private final SetRepository setRepository;

    @Autowired
    public CommandAddFavourite(ProfileHubGrpc.ProfileHubBlockingStub profileHubBlockingStub, SetRepository setRepository) {
        this.profileHubBlockingStub = profileHubBlockingStub;
        this.setRepository = setRepository;
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(SlashCommands.ADD_FAVOURITE.getName(),
                        SlashCommands.ADD_FAVOURITE.getDescription())
                .addOptions(OptionDates.SET_NUM.getOptionData());
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals(SlashCommands.ADD_FAVOURITE.getName())) return;

        var setNum = event.getOption(OptionDates.SET_NUM.getOptionData().getName()).getAsString();

        var set = setRepository.setFromId(setNum);
        var discordUserId = event.getUser().getId();

        if (set == null) {
            event.reply("Set was not found").setEphemeral(true).queue();
            return;
        }

        try {
            profileHubBlockingStub.addFavourite(Api.FavouriteSet.newBuilder()
                    .setDiscordUserId(Api.DiscordUser.newBuilder()
                            .setId(discordUserId)
                            .build())
                    .setSet(Api.Set.newBuilder()
                            .setNumber(set.number())
                            .setName(set.name())
                            .setYear(set.year())
                            .setThemeId(set.themeId())
                            .setParts(set.parts())
                            .setSetImageUrl(set.setImageUrl())
                            .setSetUrl(set.setUrl())
                            .setLastModifiedDate(set.lastModifiedDate())
                            .build())
                    .build());
        } catch (StatusRuntimeException e) {
            event.reply("Error: " + e.getMessage()).setEphemeral(true).queue();
            return;
        }

        event.reply("The set was added").setEphemeral(true).queue();
    }

}
