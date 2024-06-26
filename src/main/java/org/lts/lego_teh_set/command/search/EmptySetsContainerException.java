package org.lts.lego_teh_set.command.search;

/**
 * Throws when the list in the {@link SetsContainer} is empty.
 */
public class EmptySetsContainerException extends RuntimeException {
    public EmptySetsContainerException(String message) {
        super(message);
    }
}
