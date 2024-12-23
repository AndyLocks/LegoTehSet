package org.lts.lego_teh_set.commands.container;

import net.dv8tion.jda.api.EmbedBuilder;

import java.io.Serializable;

/// A serialized embed that contains all the information needed to display in a Discord chat
public record ContainerEmbed(int color, String description, String imageUrl, String footer) implements Serializable {
    /// @return a builder, witch can be used to create a {@link ContainerEmbed}
    public static ContainerEmbedBuilder builder() {
        return new ContainerEmbedBuilder();
    }

    /// Converts the {@link ContainerEmbed} to {@link EmbedBuilder},
    /// that contains all data from `ContainerEmbed`
    ///
    /// @param container the object from which data is collected
    /// @return a new {@link EmbedBuilder}, that contains data from `container`
    /// @see EmbedBuilder
    public static EmbedBuilder toEmbedBuilder(ContainerEmbed container) {
        return new EmbedBuilder()
                .setFooter(container.footer())
                .setDescription(container.description())
                .setImage(container.imageUrl())
                .setColor(container.color());
    }

    /// Convert this object to {@link EmbedBuilder}
    ///
    /// @return a new {@link EmbedBuilder}, that contains all data from `this` object
    /// @see EmbedBuilder
    public EmbedBuilder toEmbedBuilder() {
        return toEmbedBuilder(this);
    }

}
