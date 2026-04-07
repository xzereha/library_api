package com.example.libraryapi.controller.v1;

import com.example.libraryapi.dto.v1.BookRequestV1;
import com.example.libraryapi.dto.v1.BookResponseV1;
import com.example.libraryapi.facade.v1.BookFacadeV1;

import jakarta.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * REST controller for managing books (version 1). Provides endpoints for creating and retrieving
 * books.
 */
@RestController
@RequestMapping("/api/v1/books")
public class BookV1Controller {

    private final BookFacadeV1 bookFacade;

    /**
     * Constructor for BookV1Controller.
     *
     * @param bookFacade The BookFacadeV1 to be used by this controller
     */
    public BookV1Controller(BookFacadeV1 bookFacade) {
        this.bookFacade = bookFacade;
    }

    /**
     * Endpoint for creating a new book. Accepts a BookRequestV1 in the request body and returns a
     * BookResponseV1 with the details of the created book. The response includes a Location header
     * pointing to the URL of the newly created book resource.
     *
     * @param bookRequest The request object containing the details of the book to be created
     * @return A ResponseEntity containing the created BookResponseV1 and a Location header with the
     *     URL of the new book resource
     */
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponseV1> createBook(
            @Valid @RequestBody final BookRequestV1 bookRequest) {
        var created = bookFacade.createBook(bookRequest);

        var location = URI.create("/api/v1/books/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    /**
     * Endpoint for retrieving all books. Returns a list of BookResponseV1 containing the details of
     * all books in the system.
     *
     * @return A ResponseEntity containing a list of BookResponseV1 with the details of all books
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookResponseV1>> getBooks() {
        var books = bookFacade.getAllBooks();
        return ResponseEntity.ok(books);
    }

    /**
     * Endpoint for retrieving a book by its ID. Returns a BookResponseV1 with the details of the
     * requested book. If no book with the given ID is found, a BookNotFoundException is thrown,
     * which results in a 404 Not Found response.
     *
     * @param id The ID of the book to be retrieved
     * @return A ResponseEntity containing a BookResponseV1 with the details of the retrieved book
     * @throws com.example.libraryapi.exception.BookNotFoundException if no book with the given ID
     *     is found
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponseV1> getBookById(
            @PathVariable(name = "id", required = true) final Long id) {
        var book = bookFacade.getBookById(id);
        return ResponseEntity.ok(book);
    }
}
