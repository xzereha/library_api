package com.example.libraryapi.service;

import com.example.libraryapi.exception.AuthorNotFoundException;
import com.example.libraryapi.model.Author;
import com.example.libraryapi.model.Book;
import com.example.libraryapi.repository.AuthorRepository;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Service layer for managing authors. Provides methods to manipulate author data and retrieve
 * associated books.
 */
@Service
@Validated
public class AuthorService {
    private final AuthorRepository authorRepository;

    /**
     * Constructs a new AuthorService with the given AuthorRepository.
     *
     * @param authorRepository the repository for managing authors
     */
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Creates a new author with the given name and saves it to the repository.
     *
     * @param name the name of the author to create
     * @return the created Author object
     */
    @Nonnull
    public Author createAuthor(@Nonnull @NotBlank String name) {
        var author = new Author();
        author.setName(name);
        return authorRepository.save(author);
    }

    /**
     * Retrieves an author by their ID.
     *
     * @param id the ID of the author
     * @return the author with the given ID
     * @throws AuthorNotFoundException if the author is not found
     */
    @Nonnull
    public Author getAuthorById(@Nonnull Long id) throws AuthorNotFoundException {
        return authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
    }

    /**
     * Retrieves all books associated with the author identified by the given ID.
     *
     * @param authorId the ID of the author whose books are to be retrieved
     * @return a list of books written by the author
     * @throws AuthorNotFoundException if the author with the specified ID does not exist
     */
    @Nonnull
    public List<Book> getBooksByAuthorId(@Nonnull Long authorId) throws AuthorNotFoundException {
        var author = getAuthorById(authorId);
        return author.getBooks();
    }

    @Nonnull
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
}
