package com.example.libraryapi.service;

import java.util.Optional;

public record BookQuery(Optional<String> author, Optional<String> title, Optional<String> isbn) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Optional<String> author = Optional.empty();
        private Optional<String> title = Optional.empty();
        private Optional<String> isbn = Optional.empty();

        public Builder withAuthor(String author) {
            this.author = Optional.ofNullable(author);
            return this;
        }

        public Builder withTitle(String title) {
            this.title = Optional.ofNullable(title);
            return this;
        }

        public Builder withIsbn(String isbn) {
            this.isbn = Optional.ofNullable(isbn);
            return this;
        }

        public BookQuery build() {
            return new BookQuery(author, title, isbn);
        }
    }
}
