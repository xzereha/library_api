package com.example.libraryapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.libraryapi.exception.BookNotFoundException;
import com.example.libraryapi.model.Book;
import com.example.libraryapi.repository.BookRepository;

import jakarta.validation.constraints.NotBlank;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book createBook(@NotBlank String title, @NotBlank String author, String isbn, int publishedYear) {
        var book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPublishedYear(publishedYear);

        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }
}
