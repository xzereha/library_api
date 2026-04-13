package com.example.libraryapi.controller.v2;

import com.example.libraryapi.dto.ResponseWrapper;
import com.example.libraryapi.dto.v2.BookRequestV2;
import com.example.libraryapi.dto.v2.BookResponseV2;
import com.example.libraryapi.facade.v2.BookFacadeV2;
import com.example.libraryapi.service.BookQuery;

import jakarta.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * REST controller for managing books in API version 2. This controller provides endpoints for
 * creating new books, retrieving books by ID, and querying books based on various criteria. It uses
 * BookFacadeV2 to handle business logic and data manipulation, and it returns responses wrapped in
 * a ResponseWrapper to include metadata about the API version. The controller also supports
 * querying books with optional parameters for author, title, and ISBN, allowing clients to filter
 * books based on these criteria.
 */
@RestController
@RequestMapping("/api/v2/books")
public class BookV2Controller {
    private final BookFacadeV2 bookFacadeV2;

    /**
     * Constructs a new BookV2Controller with the given BookFacadeV2.
     *
     * @param bookFacadeV2 the facade for handling book-related business logic and data manipulation
     */
    public BookV2Controller(BookFacadeV2 bookFacadeV2) {
        this.bookFacadeV2 = bookFacadeV2;
    }

    /**
     * Endpoint for creating a new book. Accepts a BookRequestV1 payload, and returns a
     * BookResponseV2 wrapped in a ResponseWrapper with metadata about the API version. The endpoint
     * consumes and produces JSON, and it returns a 201 Created response with the location of the
     * newly created book in the Location header.
     *
     * @param bookRequest the request payload containing the book details
     * @return a ResponseEntity containing the created book wrapped in a ResponseWrapper with API
     *     version metadata
     */
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<BookResponseV2>> createBook(
            @Valid @RequestBody final BookRequestV2 bookRequest) {
        var created = bookFacadeV2.createBook(bookRequest);
        var response = new ResponseWrapper<>(created, 2);
        var location = URI.create("/api/v2/books/" + created.id());
        return ResponseEntity.created(location).body(response);
    }

    /**
     * Endpoint for retrieving books based on optional query parameters for author, title, and ISBN.
     * Returns a list of BookResponseV2 wrapped in a ResponseWrapper with metadata about the API
     * version.
     *
     * @param author the author of the books to retrieve (optional)
     * @param title the title of the books to retrieve (optional)
     * @param isbn the ISBN of the books to retrieve (optional)
     * @return a ResponseEntity containing a list of BookResponseV2 wrapped in a ResponseWrapper
     *     with API version metadata
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<List<BookResponseV2>>> getBooks(
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "isbn", required = false) String isbn) {
        var query = BookQuery.builder().withAuthor(author).withTitle(title).withIsbn(isbn).build();
        var booksByAuthor = bookFacadeV2.queryBooks(query);
        var response = new ResponseWrapper<>(booksByAuthor, 2);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint for retrieving a book by its ID. Returns a BookResponseV2 containing the details of
     * the specified book.
     *
     * @param id the ID of the book to retrieve
     * @return A ResponseEntity containing the BookResponseV2 with the details of the specified book
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<BookResponseV2>> getBookById(
            @PathVariable(name = "id", required = true) final Long id) {
        var book = bookFacadeV2.getBookById(id);
        var response = new ResponseWrapper<>(book, 2);
        return ResponseEntity.ok(response);
    }
}
