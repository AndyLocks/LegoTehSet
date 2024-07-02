package org.lts.lego_teh_set.component_creators;

import net.dv8tion.jda.api.EmbedBuilder;
import org.lts.lego_teh_set.LegoTehSetApplication;
import org.lts.lego_teh_set.rebrickableAPI.returned_objects.Set;

public class EmbedBuilderCreator {

    private static final int color = 10190047;
    private static final String authorId = "958812091488284712";

    private static final EmbedBuilder embedBuilder = new EmbedBuilder()
            .setColor(color)
            .setDescription("# Command list\n" +
                    "\n" +
                    "</search:1163119606987231342> - show all found sets by request\n" +
                    "> request\n" +
                    "> - What do you want to receive?\n" +
                    "> \n" +
                    "> ordering(optional)\n" +
                    "> - Which field to use when ordering the results\n" +
                    ">  - Year - sort by year\n" +
                    ">  - Name - sort by name\n" +
                    ">  - Number of parts - sort by number of parts" + "\n\n" +
                    "</command_list:1149416336708091934> - this message\n\n</random:1160349765201039430> - get random set\n" +
                    "> theme(optional)\n" +
                    "> - set theme")
            .setFooter(
                    String.format("%s © 2023 Все права не нужны",
                            LegoTehSetApplication.getShard()
                                    .getUserById(authorId)
                                    .getName()),
                    LegoTehSetApplication.getShard()
                            .getUserById(authorId)
                            .getAvatarUrl()
            );

    public static EmbedBuilder getEmbedBuilder(Set set) {
        var embedBuilder = new EmbedBuilder();
        var description = String.format(
                "# %s\n## %s\n\nParts: %s",
                set.getName(),
                set.getSetNum(),
                set.getNumParts()
        );

        embedBuilder.setFooter(
                String.format(
                        "Year: %d",
                        set.getYear()
                )
        );
        embedBuilder.setImage(set.getSetImageUrl());
        embedBuilder.setDescription(description);
        embedBuilder.setColor(color);

        return embedBuilder;
    }

    public static EmbedBuilder getNullErrorEmbed() {
        var embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("Not found");
        embedBuilder.setColor(color);

        return embedBuilder;
    }

    public static EmbedBuilder getCommandListEmbedBuilder() {
        return EmbedBuilderCreator.embedBuilder;
    }

}
