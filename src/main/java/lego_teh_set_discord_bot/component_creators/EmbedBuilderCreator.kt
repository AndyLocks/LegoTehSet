package lego_teh_set_discord_bot.component_creators

import net.dv8tion.jda.api.EmbedBuilder
import org.example.Main
import rebrickableAPI.returned_objects.Set

/**
 * Создает ответы для discord в виде Embed сообщений.
 */
class EmbedBuilderCreator {

    companion object{
        private var color: Int = 10190047
        private var authorId: Long = 958812091488284712

        private var embedBuilder: EmbedBuilder = EmbedBuilder()
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
            .setFooter("${Main.getShard().getUserById(authorId)?.name} © 2023 Все права не нужны", Main.getShard().getUserById(authorId)?.avatarUrl);

        /**
         * Заполняет вложенное сообщение данными из набора
         *
         * @param set набор, для которого нужно создать ответ
         * @return {@link EmbedBuilder} заполненный данными о наборе
         */
        public fun getEmbedBuilder(set: Set): EmbedBuilder {

            val embedBuilder: EmbedBuilder = EmbedBuilder()
            val description: String = "# ${set.name}\n## ${set.setNum}\n\nParts: ${set.numParts}"
            embedBuilder.setFooter("Year: ${set.year.toString()}")
            embedBuilder.setImage(set.setImageUrl)
            embedBuilder.setDescription(description)
            embedBuilder.setColor(color)

            return embedBuilder
        }

        /**
         * Дает сообщение об ошибке, когда набор не найден.
         */
        public fun getNullErrorEmbed(): EmbedBuilder {

            val embedBuilder: EmbedBuilder = EmbedBuilder()
            embedBuilder.setTitle("Not found")
            embedBuilder.setColor(color)

            return embedBuilder
        }

        /**
         * Дает список всех команд бота
         */
        public fun getCommandListEmbedBuilder(): EmbedBuilder {
            return this.embedBuilder
        }
    }
}