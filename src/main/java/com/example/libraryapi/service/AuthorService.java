package com.example.libraryapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.libraryapi.model.Author;
import com.example.libraryapi.repository.AuthorRepository;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author createAuthor(String name) {
        var author = new Author();
        author.setName(name);
        return authorRepository.save(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
}
