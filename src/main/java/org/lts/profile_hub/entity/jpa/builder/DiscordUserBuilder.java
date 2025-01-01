package org.lts.profile_hub.entity.jpa.builder;

import org.lts.profile_hub.entity.jpa.Set;
import org.lts.profile_hub.entity.jpa.DiscordUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DiscordUserBuilder {

    private UUID id;
    private String discordId;
    private List<Set> favouriteSets;

    public DiscordUserBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public DiscordUserBuilder discordId(String discordId) {
        this.discordId = discordId;
        return this;
    }

    public DiscordUserBuilder favouriteSets(List<Set> favouriteSets) {
        this.favouriteSets = new ArrayList<>(favouriteSets);
        return this;
    }

    public DiscordUser build() {
        return new DiscordUser(id, discordId, favouriteSets);
    }

}
