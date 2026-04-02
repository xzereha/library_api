package com.example.libraryapi.service;

import com.example.libraryapi.exception.AuthorNotFoundException;
import com.example.libraryapi.exception.BookNotFoundException;
import com.example.libraryapi.model.Author;
import com.example.libraryapi.model.Book;
import com.example.libraryapi.repository.AuthorRepository;
import com.example.libraryapi.repository.BookRepository;
import com.example.libraryapi.repository.BookSpecifications;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer implementation for managing books. Provides methods to manipulate book data and
 * retrieve book information. This service1 interacts with the BookRepository and AuthorRepository
 * to perform CRUD operations and handle business logic related to books and their associated
 * authors.
 */
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    /**
     * Constructs a new BookServiceImpl with the given BookRepository and AuthorRepository.
     *
     * @param bookRepository the repository for managing books
     * @param authorRepository the repository for managing authors
     */
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

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
    @CacheEvict(value = "Books", allEntries = true)
    public Book createBook(
            @Nonnull @NotBlank String title,
            @Nonnull Author author,
            String isbn,
            Integer publishedYear) {
        var book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPublishedYear(publishedYear);
        return bookRepository.save(book);
    }

    /**
     * Creates a new book with the given title, author ID, ISBN, and published year. The author is
     * provided as an ID, and the service will handle finding the corresponding Author entity.
     *
     * @param title the title of the book
     * @param authorId the ID of the author of the book
     * @param isbn the ISBN of the book
     * @param publishedYear the year the book was published
     * @return the created Book object
     * @throws AuthorNotFoundException if no author with the given ID is found
     */
    @Nonnull
    @CacheEvict(value = "Books", allEntries = true)
    public Book createBook(String title, Long authorId, String isbn, Integer publishedYear) {
        var author =
                authorRepository
                        .findById(authorId)
                        .orElseThrow(() -> new AuthorNotFoundException(authorId));
        return createBook(title, author, isbn, publishedYear);
    }

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
    @CacheEvict(value = "Books", allEntries = true)
    public Book createBook(
            @Nonnull @NotBlank String title,
            @Nonnull @NotBlank String author,
            String isbn,
            Integer publishedYear) {
        Author authorEntity = authorRepository.findByName(author);
        // TODO: This is a hacky solution to keep V1 working
        if (authorEntity == null) {
            authorEntity = new Author();
            authorEntity.setName(author);
            authorEntity = authorRepository.save(authorEntity);
        }
        return createBook(title, authorEntity, isbn, publishedYear);
    }

    /**
     * Retrieves all books from the repository.
     *
     * @return a list of all books
     */
    @Nonnull
    @Cacheable("Books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves a book by its ID. If the book is not found, a BookNotFoundException is thrown.
     *
     * @param id the ID of the book to retrieve
     * @return the book with the specified ID
     * @throws BookNotFoundException if no book with the given ID is found
     */
    @Nonnull
    @Cacheable("Books")
    public Book getBookById(@Nonnull Long id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

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
    public List<Book> queryBooks(@Nonnull BookQuery query) {
        var spec = BookSpecifications.fromQuery(query);
        return bookRepository.findAll(spec);
    }
}
