package lego_teh_set_discord_bot.commands.search;

/**
 * Возникает в случаях, когда список в {@link SetsContainer} оказывается пустым.
 */
public class EmptySetsContainerException extends RuntimeException {
    public EmptySetsContainerException(String message) {
        super(message);
    }
}
