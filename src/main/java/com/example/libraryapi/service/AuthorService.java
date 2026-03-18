package com.example.libraryapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.libraryapi.exception.AuthorNotFoundException;
import com.example.libraryapi.model.Author;
import com.example.libraryapi.repository.AuthorRepository;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Nonnull
    public Author createAuthor(@Nonnull @NotBlank String name) {
        var author = new Author();
        author.setName(name);
        return authorRepository.save(author);
    }

    @Nonnull
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Nonnull
    public Author getAuthorById(@Nonnull Long id) throws AuthorNotFoundException {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }
}
