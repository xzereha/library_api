package com.example.libraryapi.service;

import com.example.libraryapi.exception.BookNotFoundException;
import com.example.libraryapi.model.Author;
import com.example.libraryapi.model.Book;
import com.example.libraryapi.repository.AuthorRepository;
import com.example.libraryapi.repository.BookRepository;
import com.example.libraryapi.repository.BookSpecifications;

import jakarta.validation.constraints.NotBlank;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer implementation for managing books. Provides methods to manipulate book data and
 * retrieve book information. This implementation interacts with the BookRepository and
 * AuthorRepository to perform CRUD operations and handle business logic related to books and their
 * associated authors.
 */
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    /**
     * Constructs a new BookServiceImpl with the given BookRepository and AuthorRepository.
     *
     * @param bookRepository the repository for managing books
     * @param authorRepository the repository for managing authors
     */
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Book createBook(
            @NotBlank String title, @NotBlank Author author, String isbn, Integer publishedYear) {
        var book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPublishedYear(publishedYear);

        return bookRepository.save(book);
    }

    @Override
    public Book createBook(
            @NotBlank String title, @NotBlank String author, String isbn, Integer publishedYear) {
        Author authorEntity = authorRepository.findByName(author);
        // TODO: This is a hacky solution to keep V1 working
        if (authorEntity == null) {
            authorEntity = new Author();
            authorEntity.setName(author);
            authorEntity = authorRepository.save(authorEntity);
        }
        return createBook(title, authorEntity, isbn, publishedYear);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public List<Book> queryBooks(BookQuery query) {
        var spec = BookSpecifications.fromQuery(query);
        return bookRepository.findAll(spec);
    }
}
