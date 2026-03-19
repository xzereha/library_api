package com.example.libraryapi.service;

import java.util.Optional;

/**
 * A query object for searching books based on optional criteria such as author, title, and ISBN.
 * This class uses the Builder pattern to allow for flexible construction of query objects with
 * varying criteria.
 */
public record BookQuery(Optional<String> author, Optional<String> title, Optional<String> isbn) {

    /**
     * Creates a new Builder instance for constructing a BookQuery.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder class for constructing BookQuery instances with optional criteria. */
    public static class Builder {
        private Optional<String> author = Optional.empty();
        private Optional<String> title = Optional.empty();
        private Optional<String> isbn = Optional.empty();

        /**
         * Sets the author criterion for the BookQuery.
         *
         * @param author the author to search for (optional)
         * @return the Builder instance for chaining
         */
        public Builder withAuthor(String author) {
            this.author = Optional.ofNullable(author);
            return this;
        }

        /**
         * Sets the title criterion for the BookQuery.
         *
         * @param title the title to search for (optional)
         * @return the Builder instance for chaining
         */
        public Builder withTitle(String title) {
            this.title = Optional.ofNullable(title);
            return this;
        }

        /**
         * Sets the ISBN criterion for the BookQuery.
         *
         * @param isbn the ISBN to search for (optional)
         * @return the Builder instance for chaining
         */
        public Builder withIsbn(String isbn) {
            this.isbn = Optional.ofNullable(isbn);
            return this;
        }

        /**
         * Builds the BookQuery instance with the specified criteria.
         *
         * @return a new BookQuery instance
         */
        public BookQuery build() {
            return new BookQuery(author, title, isbn);
        }
    }
}
