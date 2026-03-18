package com.example.libraryapi.controller.v2;

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

import com.example.libraryapi.dto.ResponseWrapper;
import com.example.libraryapi.dto.v2.AuthorRequestV2;
import com.example.libraryapi.dto.v2.AuthorResponseV2;
import com.example.libraryapi.dto.v2.BookResponseV2;
import com.example.libraryapi.service.AuthorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/authors")
public class AuthorV2Controller {
    private final AuthorService authorService;

    public AuthorV2Controller(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<AuthorResponseV2>> createAuthor(
            @Valid @RequestBody final AuthorRequestV2 authorRequest) {
        var author = authorService.createAuthor(authorRequest.name());
        var dto = new AuthorResponseV2(author.getId(), author.getName());
        var response = new ResponseWrapper<>(dto, 2);
        var location = URI.create("/api/v2/authors/" + dto.id());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<List<AuthorResponseV2>>> getAuthors() {
        var authors = authorService.getAllAuthors();
        var dtos = authors.stream()
                .map(author -> new AuthorResponseV2(author.getId(), author.getName()))
                .toList();
        var response = new ResponseWrapper<>(dtos, 2);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<AuthorResponseV2>> getAuthorById(@PathVariable final Long id) {
        var author = authorService.getAuthorById(id);
        var dto = new AuthorResponseV2(author.getId(), author.getName());
        var response = new ResponseWrapper<>(dto, 2);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<List<BookResponseV2>>> getBooksByAuthorId(@PathVariable final Long id) {
        var books = authorService.getBooksByAuthorId(id);
        var dtos = books.stream()
                .map(BookResponseV2::fromBook)
                .toList();
        var response = new ResponseWrapper<>(dtos, 2);
        return ResponseEntity.ok(response);
    }
}
