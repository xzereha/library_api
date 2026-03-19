package com.example.libraryapi.facade.v2;

import com.example.libraryapi.dto.v1.BookRequestV1;
import com.example.libraryapi.dto.v2.BookResponseV2;
import com.example.libraryapi.service.BookQuery;
import com.example.libraryapi.service.BookService;
import java.util.List;
import org.springframework.stereotype.Service;

/** Facade for book-related operations (version 2). */
@Service
public class BookFacadeV2 {
    private final BookService bookService;

    /**
     * Constructor for BookFacadeV2.
     *
     * @param bookService The BookService to be used by this facade
     */
    public BookFacadeV2(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Creates a new book based on the provided BookRequestV1 and returns a BookResponseV2.
     *
     * @param bookRequestV1 The request object containing the details of the book to be created
     * @return A BookResponseV2 containing the details of the created book
     */
    public BookResponseV2 createBook(BookRequestV1 bookRequestV1) {
        var book =
                bookService.createBook(
                        bookRequestV1.title(),
                        bookRequestV1.author(),
                        bookRequestV1.isbn(),
                        bookRequestV1.publishedYear());
        return BookResponseV2.fromBook(book);
    }

    /**
     * Retrieves all books and returns a list of BookResponseV2.
     *
     * @return A list of BookResponseV2 containing the details of all books
     */
    public List<BookResponseV2> getAllBooks() {
        var books = bookService.getAllBooks();
        return books.stream().map(BookResponseV2::fromBook).toList();
    }

    /**
     * Retrieves books based on the provided query parameters and returns a list of BookResponseV2.
     *
     * @param query The query object containing the search criteria for books
     * @return A list of BookResponseV2 containing the details of the books that match the query
     *     criteria
     */
    public List<BookResponseV2> queryBooks(BookQuery query) {
        var books = bookService.queryBooks(query);
        return books.stream().map(BookResponseV2::fromBook).toList();
    }

    /**
     * Retrieves a book by its ID and returns a BookResponseV2.
     *
     * @param id The ID of the book to be retrieved
     * @return A BookResponseV2 containing the details of the retrieved book
     * @throws com.example.libraryapi.exception.BookNotFoundException if no book with the given ID
     *     is found
     */
    public BookResponseV2 getBookById(Long id) {
        var book = bookService.getBookById(id);
        return BookResponseV2.fromBook(book);
    }
}
