package com.example.libraryapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.libraryapi.exception.BookNotFoundException;
import com.example.libraryapi.model.Author;
import com.example.libraryapi.model.Book;
import com.example.libraryapi.repository.AuthorRepository;
import com.example.libraryapi.repository.BookRepository;
import com.example.libraryapi.repository.BookSpecifications;

import jakarta.validation.constraints.NotBlank;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Book createBook(@NotBlank String title, @NotBlank Author author, String isbn, Integer publishedYear) {
        var book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPublishedYear(publishedYear);

        return bookRepository.save(book);
    }

    @Override
    public Book createBook(@NotBlank String title, @NotBlank String author, String isbn, Integer publishedYear) {
        var authorEntity = authorRepository.findByName(author);
        // TODO: handle case when author is not found
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
