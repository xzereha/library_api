package com.example.libraryapi.controller.v2;

import com.example.libraryapi.dto.ResponseWrapper;
import com.example.libraryapi.dto.v2.AuthorRequestV2;
import com.example.libraryapi.dto.v2.AuthorResponseV2;
import com.example.libraryapi.dto.v2.BookResponseV2;
import com.example.libraryapi.service.AuthorService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for managing authors in API version 2. This controller provides endpoints for
 * creating new authors, retrieving authors by ID, and querying authors based on various criteria.
 * It uses AuthorService to handle business logic and data manipulation, and it returns responses
 * wrapped in a ResponseWrapper to include metadata about the API version.
 */
@RestController
@RequestMapping("/api/v2/authors")
public class AuthorV2Controller {
    private final AuthorService authorService;

    /**
     * Constructs a new AuthorV2Controller with the given AuthorService.
     *
     * @param authorService the service for handling author-related business logic and data
     *     manipulation
     */
    public AuthorV2Controller(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Endpoint for creating a new author. Accepts an AuthorRequestV2 payload, and returns an
     * AuthorResponseV2 wrapped in a ResponseWrapper with metadata about the API version. The
     * endpoint consumes and produces JSON, and it returns a 201 Created response with the location
     * of the newly created author in the Location header.
     *
     * @param authorRequest the request payload containing the author details
     * @return a ResponseEntity containing the created author wrapped in a ResponseWrapper with API
     *     version metadata
     */
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<AuthorResponseV2>> createAuthor(
            @Valid @RequestBody final AuthorRequestV2 authorRequest) {
        var author = authorService.createAuthor(authorRequest.name());
        var dto = new AuthorResponseV2(author.getId(), author.getName());
        var response = new ResponseWrapper<>(dto, 2);
        var location = URI.create("/api/v2/authors/" + dto.id());
        return ResponseEntity.created(location).body(response);
    }

    /**
     * Endpoint for retrieving all authors. Returns a list of AuthorResponseV2 containing the
     * details of all authors in the system.
     *
     * @return A ResponseEntity containing a list of AuthorResponseV2 with the details of all
     *     authors
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<List<AuthorResponseV2>>> getAuthors() {
        var authors = authorService.getAllAuthors();
        var dtos =
                authors.stream()
                        .map(author -> new AuthorResponseV2(author.getId(), author.getName()))
                        .toList();
        var response = new ResponseWrapper<>(dtos, 2);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint for retrieving an author by their ID. Returns an AuthorResponseV2 containing the
     * details of the specified author.
     *
     * @param id the ID of the author to retrieve
     * @return A ResponseEntity containing the AuthorResponseV2 with the details of the specified
     *     author
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<AuthorResponseV2>> getAuthorById(
            @PathVariable final Long id) {
        var author = authorService.getAuthorById(id);
        var dto = new AuthorResponseV2(author.getId(), author.getName());
        var response = new ResponseWrapper<>(dto, 2);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint for retrieving all books written by the author identified by the given ID. Returns a
     * list of BookResponseV2 containing the details of the books written by the specified author.
     *
     * @param id the ID of the author whose books are to be retrieved
     * @return A ResponseEntity containing a list of BookResponseV2 with the details of the books
     *     written by the specified author
     */
    @GetMapping(value = "/{id}/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<List<BookResponseV2>>> getBooksByAuthorId(
            @PathVariable final Long id) {
        var books = authorService.getBooksByAuthorId(id);
        var dtos = books.stream().map(BookResponseV2::fromBook).toList();
        var response = new ResponseWrapper<>(dtos, 2);
        return ResponseEntity.ok(response);
    }
}
