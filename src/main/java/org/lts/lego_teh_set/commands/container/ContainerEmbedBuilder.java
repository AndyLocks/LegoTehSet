package org.lts.lego_teh_set.commands.container;

/// A builder for {@link ContainerEmbed}
///
/// All fields in it are null by default
public class ContainerEmbedBuilder {
    private Integer color = null;
    private String description = null;
    private String imageUrl = null;
    private String footer = null;

    public static ContainerEmbedBuilder builder() {
        return new ContainerEmbedBuilder();
    }

    public ContainerEmbedBuilder color(int color) {
        this.color = color;
        return this;
    }

    public ContainerEmbedBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ContainerEmbedBuilder imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public ContainerEmbedBuilder footer(String footer) {
        this.footer = footer;
        return this;
    }

    public ContainerEmbed build() {
        return new ContainerEmbed(color, description, imageUrl, footer);
    }

}
