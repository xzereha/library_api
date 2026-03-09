package com.example.libraryapi.controller;

import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.libraryapi.dto.v1.BookRequestV1;
import com.example.libraryapi.dto.v1.BookResponseV1;
import com.example.libraryapi.facade.v1.BookFacadeV1;

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
    public ResponseEntity<BookResponseV1> createBook(@Validated @RequestBody BookRequestV1 bookRequest) {
        var created = bookFacade.createBook(bookRequest);

        var location = URI.create("/api/v1/books/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }
    
    @GetMapping
    public String getBooks() {
        return "List of books (v1)";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable Long id) {
        return "Book details (v1) for ID: " + id;
    }
}
