package com.example.libraryapi.service;

import com.example.libraryapi.exception.BookNotFoundException;
import com.example.libraryapi.model.Author;
import com.example.libraryapi.model.Book;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * Service layer interface for managing books. Provides methods to manipulate book data and retrieve
 * book information.
 */
public interface BookService {
    /**
     * Creates a new book with the given title, author, ISBN, and published year. The author is
     * provided as an Author entity.
     *
     * @param title the title of the book
     * @param author the author of the book
     * @param isbn the ISBN of the book
     * @param publishedYear the year the book was published
     * @return the created Book object
     */
    @Nonnull
    Book createBook(
            @Nonnull @NotBlank String title,
            @Nonnull Author author,
            String isbn,
            Integer publishedYear);

    /**
     * Creates a new book with the given title, author name, ISBN, and published year. The author is
     * provided as a string, and the service will handle finding or creating the corresponding
     * Author entity.
     *
     * <p>This method is intended to support the older version of the API (V1) where the author is
     * provided as a string rather than an Author entity. Prefer using the other createBook method
     * for newer API versions to ensure proper handling of author data and relationships. The
     * service will attempt to find an existing Author by name, and if not found, it will create a
     * new Author entity with the provided name. This is a temporary solution to maintain
     * compatibility with V1 and should be refactored in the future to encourage proper use of
     * Author entities in API requests.
     *
     * @param title the title of the book
     * @param author the name of the author
     * @param isbn the ISBN of the book
     * @param publishedYear the year the book was published
     * @return the created Book object
     * @deprecated since version 2.0, use {@link #createBook(String, Author, String, Integer)}
     *     instead for better handling of author data and relationships. This method is retained for
     *     backward compatibility with API version 1 and will be removed in a future release.
     */
    @Deprecated(since = "2.0", forRemoval = true)
    @Nonnull
    Book createBook(
            @Nonnull @NotBlank String title,
            @Nonnull @NotBlank String author,
            String isbn,
            Integer publishedYear);

    /**
     * Retrieves all books from the repository.
     *
     * @return a list of all books
     */
    @Nonnull
    List<Book> getAllBooks();

    /**
     * Retrieves a book by its ID. If the book is not found, a BookNotFoundException is thrown.
     *
     * @param id the ID of the book to retrieve
     * @return the book with the specified ID
     * @throws BookNotFoundException if no book with the given ID is found
     */
    @Nonnull
    Book getBookById(@Nonnull Long id) throws BookNotFoundException;

    /**
     * Queries books based on the specified criteria in the BookQuery object. This method allows for
     * flexible searching and filtering of books based on various attributes such as title, author,
     * ISBN, and published year. The implementation of this method will utilize the
     * BookSpecifications to build dynamic queries based on the provided criteria. The result will
     * be a list of books that match the specified query parameters.
     *
     * @param query the criteria for querying books
     * @return a list of books that match the specified query criteria
     */
    @Nonnull
    List<Book> queryBooks(@Nonnull BookQuery query);
}
