package org.lts.lego_teh_set.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import org.lts.lego_teh_set.rebrickableAPI.dto.Set;

/// Contains standard embeds
public class EmbedBuilderCreator {

    private static final int color = 10190047;
    private static final String authorId = "958812091488284712";

    private static final EmbedBuilder commandList = new EmbedBuilder()
            .setColor(color)
            .setDescription("""
                    # Command list
                    
                    ## Search
                    
                    </search:1163119606987231342> - shows all sets found by request
                    
                    ### Arguments
                    
                    `request` - what do you want to receive
                    `ordering` (optional) - sorting way
                    > - year - sort by year
                    > - name - sort by name
                    > - number of parts - sort by number of parts
                    
                    ## Command list
                    
                    </command_list:1149416336708091934> - this message
                    
                    ## Random
                    
                    </random:1160349765201039430> - get random set
                    
                    ### Arguments
                    
                    `theme` (optional) - set theme""");

    private static final EmbedBuilder nullErrorEmbedBuilder = new EmbedBuilder()
            .setColor(color)
            .setTitle("Not found");

    /// Converts a set to {@link EmbedBuilder}, which contains all the data from the `set`.
    ///
    /// @param set a set to convert
    /// @return {@link EmbedBuilder}, which contains all the data from the `set`
    public static EmbedBuilder fromSet(Set set) {
        return new EmbedBuilder()
                .setDescription(String.format("# %s\n## %s\n\nParts: %s",
                        set.name(),
                        set.number(),
                        set.parts()))
                .setColor(color)
                .setImage(set.setImageUrl())
                .setFooter(String.format("Year: %d", set.year()));
    }

    /// @return standard message when the `search` command ({@link org.lts.lego_teh_set.commands.implementations.CommandSearch}) does not find anything
    public static EmbedBuilder getNullErrorEmbedBuilder() {
        return nullErrorEmbedBuilder;
    }

    /// @return standard message for the `command_list` command ({@link org.lts.lego_teh_set.commands.implementations.CommandCommandList})
    public static EmbedBuilder getCommandListEmbedBuilder() {
        return commandList;
    }

}
