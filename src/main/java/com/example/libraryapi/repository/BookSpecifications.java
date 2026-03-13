package com.example.libraryapi.repository;

import java.util.Locale;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.example.libraryapi.model.Book;
import com.example.libraryapi.service.BookQuery;

public final class BookSpecifications {
    private BookSpecifications() {
    }

    public static Specification<Book> fromQuery(BookQuery query) {
        Specification<Book> spec = (root, q, cb) -> cb.conjunction();

        // Ensure that we handle null and empty strings by normalizing the input parameters
        var author = normalize(query.author().orElse(null));
        var title = normalize(query.title().orElse(null));
        var isbn = normalize(query.isbn().orElse(null));

        if (author.isPresent()) {
            spec = spec.and(authorContains(author.get()));
        }
        if (title.isPresent()) {
            spec = spec.and(titleContains(title.get()));
        }
        if (isbn.isPresent()) {
            spec = spec.and(isbnEquals(isbn.get()));
        }

        return spec;
    }

    public static Specification<Book> authorContains(String author) {
        return (root, query, cb) -> cb.like(
                cb.lower(root.get("author")),
                "%" + author.toLowerCase(Locale.ROOT) + "%");
    }

    public static Specification<Book> titleContains(String title) {
        return (root, query, cb) -> cb.like(
                cb.lower(root.get("title")),
                "%" + title.toLowerCase(Locale.ROOT) + "%");
    }

    public static Specification<Book> isbnEquals(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    /**
     * Helper method to normalize string values by trimming and converting empty strings to Optional.empty()
     *
     * @param value
     *            the string value to normalize
     * @return an Optional containing the normalized string, or Optional.empty() if the string is null or empty
     */
    private static Optional<String> normalize(String value) {
        return Optional.ofNullable(value)
                .map(String::trim)
                .filter(s -> !s.isEmpty());
    }
}
