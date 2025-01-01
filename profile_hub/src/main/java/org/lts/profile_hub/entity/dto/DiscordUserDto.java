package org.lts.profile_hub.entity.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record DiscordUserDto(UUID id, String discordId, List<SetDto> favouriteSets) {

    public static DiscordUserDto fromJpa(org.lts.profile_hub.entity.jpa.DiscordUser user) {
        return new DiscordUserDto(user.getId(), user.getDiscordId(),
                new ArrayList<>(user.getFavouriteSets() == null ? List.of() : user.getFavouriteSets()).stream()
                        .map(SetDto::from)
                        .collect(Collectors.toList()));
    }

    public static org.lts.profile_hub.entity.jpa.DiscordUser toJpa(DiscordUserDto dto) {
        return org.lts.profile_hub.entity.jpa.DiscordUser.builder()
                .id(dto.id())
                .discordId(dto.discordId())
                .favouriteSets(dto.favouriteSets().stream()
                        .map(set -> set.toJpa())
                        .collect(Collectors.toList()))
                .build();
    }

    public org.lts.profile_hub.entity.jpa.DiscordUser toJpa() {
        return toJpa(this);
    }

}
