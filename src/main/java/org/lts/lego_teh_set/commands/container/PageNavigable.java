package org.lts.lego_teh_set.commands.container;

/// An object that can be flipped through like a book.
public interface PageNavigable {
    /// Next page.
    ///
    /// # Implementation
    ///
    /// - Can be invoked an infinite number of times.
    void next();

    /// Previous page.
    ///
    /// # Implementation
    ///
    /// - Can be invoked an infinite number of times.
    void previous();

    /// Teleports to the very end
    void toEnd();

    /// Teleports to the very start
    void toStart();

    /// Sets the page
    ///
    /// @throws IllegalArgumentException if page number is less than 0 or greater than the number of sets.
    void setPage(int page);

    /// @return true if there is a next page
    boolean hasNext();

    /// @return true if there is a previous page
    boolean hasPrevious();
}
