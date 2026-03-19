package com.example.libraryapi.facade.v1;

import com.example.libraryapi.dto.v1.BookRequestV1;
import com.example.libraryapi.dto.v1.BookResponseV1;
import com.example.libraryapi.service.BookService;

import org.springframework.stereotype.Service;

import java.util.List;

/** Facade for book-related operations (version 1). */
@Service
public class BookFacadeV1 {
    private final BookService bookService;

    /**
     * Constructor for BookFacadeV1.
     *
     * @param bookService The BookService to be used by this facade
     */
    public BookFacadeV1(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Creates a new book based on the provided BookRequestV1 and returns a BookResponseV1.
     *
     * @param bookRequest The request object containing the details of the book to be created
     * @return A BookResponseV1 containing the details of the created book
     */
    public BookResponseV1 createBook(BookRequestV1 bookRequest) {
        var book =
                bookService.createBook(
                        bookRequest.title(),
                        bookRequest.author(),
                        bookRequest.isbn(),
                        bookRequest.publishedYear());
        return new BookResponseV1(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getName(),
                book.getIsbn(),
                book.getPublishedYear());
    }

    /**
     * Retrieves all books and returns a list of BookResponseV1.
     *
     * @return A list of BookResponseV1 containing the details of all books
     */
    public List<BookResponseV1> getAllBooks() {
        var books = bookService.getAllBooks();
        return books.stream().map(BookResponseV1::fromBook).toList();
    }

    /**
     * Retrieves a book by its ID and returns a BookResponseV1.
     *
     * @param id The ID of the book to be retrieved
     * @return A BookResponseV1 containing the details of the retrieved book
     * @throws com.example.libraryapi.exception.BookNotFoundException if no book with the given ID
     *     is found
     */
    public BookResponseV1 getBookById(Long id) {
        var book = bookService.getBookById(id);
        return new BookResponseV1(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getName(),
                book.getIsbn(),
                book.getPublishedYear());
    }
}
