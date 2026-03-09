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
import org.springframework.web.bind.annotation.RestController;

import com.example.libraryapi.dto.v1.BookRequestV1;
import com.example.libraryapi.dto.v1.BookResponseV1;
import com.example.libraryapi.facade.v1.BookFacadeV1;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/books")
public class BookV1Controller {
    
    private final BookFacadeV1 bookFacade;

    public BookV1Controller(BookFacadeV1 bookFacade) {
        this.bookFacade = bookFacade;
    }

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BookResponseV1> createBook(@Valid @RequestBody final BookRequestV1 bookRequest) {
        var created = bookFacade.createBook(bookRequest);

        var location = URI.create("/api/v1/books/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }
    
    @GetMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<BookResponseV1>> getBooks() {
        var books = bookFacade.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BookResponseV1> getBookById(@PathVariable final Long id) {
        var book = bookFacade.getBookById(id);
        return ResponseEntity.ok(book);
    }
}
