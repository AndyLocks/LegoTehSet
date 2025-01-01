package org.lts.profile_hub.entity.jpa;

import jakarta.persistence.*;
import org.lts.profile_hub.entity.jpa.builder.DiscordUserBuilder;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "discord_user")
public class DiscordUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "discord_id", unique = true, nullable = false)
    private String discordId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "favourite_set",
            joinColumns = @JoinColumn(name = "discord_user_id"),
            inverseJoinColumns = @JoinColumn(name = "set_id"))
    private List<Set> favouriteSets;

    public static DiscordUserBuilder builder() {
        return new DiscordUserBuilder();
    }

    public static Example<DiscordUser> exampleWithDiscordId(String discordId) {
        return Example.of(DiscordUser.builder()
                        .discordId(discordId)
                        .build(),
                ExampleMatcher.matching()
                        .withIgnoreNullValues());
    }

    public DiscordUser() {
    }

    public DiscordUser(UUID id, String discordId, List<Set> favouriteSets) {
        this.id = id;
        this.discordId = discordId;
        this.favouriteSets = favouriteSets;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDiscordId() {
        return discordId;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }

    public List<Set> getFavouriteSets() {
        return favouriteSets;
    }

    public void setFavouriteSets(List<Set> favouriteSets) {
        this.favouriteSets = favouriteSets;
    }

    @Override
    public String toString() {
        return "DiscordUser{" +
                "id=" + id +
                ", discordId='" + discordId + '\'' +
                ", favouriteSets=" + favouriteSets +
                '}';
    }

}
