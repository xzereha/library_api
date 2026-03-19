package com.example.libraryapi.repository;

import com.example.libraryapi.model.Book;
import com.example.libraryapi.service.BookQuery;
import java.util.Locale;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;

/** Utility class for creating JPA Specifications for the Book entity. */
public final class BookSpecifications {
    private BookSpecifications() {}

    /**
     * Creates a Specification for querying books based on the provided BookQuery object. The
     * resulting Specification will include conditions for author name, book title, and ISBN if they
     * are present in the query. The author and title conditions will be case-insensitive and will
     * match any part of the respective fields, while the ISBN condition will require an exact
     * match.
     *
     * @param query The BookQuery object containing the search criteria for books
     * @return A Specification<Book> that can be used to query the database for books matching the
     *     criteria
     */
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

    /**
     * Creates a Specification for querying books based on the author's name. The resulting
     * Specification will match books where the author's name contains the specified string,
     * ignoring case.
     *
     * @param author The string to search for in the author's name
     * @return A Specification<Book> that can be used to query the database for books with authors
     *     whose names contain the specified string
     */
    public static Specification<Book> authorContains(String author) {
        return (root, query, cb) -> {
            var authorJoin = root.join("author");
            return cb.like(
                    cb.lower(authorJoin.get("name")), "%" + author.toLowerCase(Locale.ROOT) + "%");
        };
    }

    /**
     * Creates a Specification for querying books based on the book title. The resulting
     * Specification will match books where the title contains the specified string, ignoring case.
     *
     * @param title The string to search for in the book title
     * @return A Specification<Book> that can be used to query the database for books with titles
     *     containing the specified string
     */
    public static Specification<Book> titleContains(String title) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase(Locale.ROOT) + "%");
    }

    /**
     * Creates a Specification for querying books based on the ISBN. The resulting Specification
     * will match books where the ISBN is exactly equal to the specified string.
     *
     * @param isbn The string to match against the book's ISBN
     * @return A Specification<Book> that can be used to query the database for books with an ISBN
     *     exactly equal to the specified string
     */
    public static Specification<Book> isbnEquals(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    /**
     * Helper method to normalize string values by trimming and converting empty strings to
     * Optional.empty().
     *
     * @param value the string value to normalize
     * @return an Optional containing the normalized string, or Optional.empty() if the string is
     *     null or empty
     */
    private static Optional<String> normalize(String value) {
        return Optional.ofNullable(value).map(String::trim).filter(s -> !s.isEmpty());
    }
}
