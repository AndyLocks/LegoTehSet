package lego_teh_set_discord_bot.component_creators

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.User
import org.example.Main
import rebrickableAPI.returned_objects.Set
import java.awt.Color

class EmbedBuilderCreator {

    companion object{
        private var color: Int = 10190047
        private var authorId: Long = 958812091488284712

        public fun getEmbedBuilder(set: Set): EmbedBuilder {

            val embedBuilder: EmbedBuilder = EmbedBuilder()
            val description: String = "# ${set.name}\n## ${set.setNum}\n\nParts: ${set.numParts}"
            embedBuilder.setFooter("Year: ${set.year.toString()}")
            embedBuilder.setImage(set.setImageUrl)
            embedBuilder.setDescription(description)
            embedBuilder.setColor(color)

            return embedBuilder
        }

        public fun getNullErrorEmbed(): EmbedBuilder {

            val embedBuilder: EmbedBuilder = EmbedBuilder()
            embedBuilder.setTitle("Not found")
            embedBuilder.setColor(color)

            return embedBuilder
        }

        public fun getCommandListEmbedBuilder(): EmbedBuilder {

            val embedBuilder: EmbedBuilder = EmbedBuilder()
            embedBuilder.setColor(color)
            embedBuilder.setDescription("# Command list\n" +
                    "\n" +
                    "</set:1071455594378178650> - find a set on request\n" +
                    "> request\n" +
                    "> - What do you want to receive?\n" +
                    "\n" +
                    "</command_list:1149416336708091934> - this message")

            val author: User? = Main.getShard().getUserById(authorId)
            embedBuilder.setFooter("${author?.name} © 2023 Все права не нужны", author?.avatarUrl)

            return embedBuilder
        }
    }
}