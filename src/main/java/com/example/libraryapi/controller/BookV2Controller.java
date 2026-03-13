package com.example.libraryapi.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.libraryapi.dto.ResponseWrapper;
import com.example.libraryapi.dto.v1.BookRequestV1;
import com.example.libraryapi.dto.v2.BookResponseV2;
import com.example.libraryapi.facade.v2.BookFacadeV2;
import com.example.libraryapi.service.BookQuery;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/books")
public class BookV2Controller {
    private final BookFacadeV2 bookFacadeV2;

    public BookV2Controller(BookFacadeV2 bookFacadeV2) {
        this.bookFacadeV2 = bookFacadeV2;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<BookResponseV2>> createBook(
            @Valid @RequestBody final BookRequestV1 bookRequest) {
        var created = bookFacadeV2.createBook(bookRequest);
        var response = new ResponseWrapper<>(created, 2);
        var location = URI.create("/api/v2/books/" + created.id());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<List<BookResponseV2>>> getBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String isbn) {
        var query = BookQuery.builder()
                .withAuthor(author)
                .withTitle(title)
                .withIsbn(isbn)
                .build();
        var booksByAuthor = bookFacadeV2.queryBooks(query);
        var response = new ResponseWrapper<>(booksByAuthor, 2);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<BookResponseV2>> getBookById(@PathVariable final Long id) {
        var book = bookFacadeV2.getBookById(id);
        var response = new ResponseWrapper<>(book, 2);
        return ResponseEntity.ok(response);
    }
}
