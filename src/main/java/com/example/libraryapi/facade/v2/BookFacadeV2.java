package com.example.libraryapi.facade.v2;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.libraryapi.dto.v1.BookRequestV1;
import com.example.libraryapi.dto.v2.BookResponseV2;
import com.example.libraryapi.service.BookQuery;
import com.example.libraryapi.service.BookService;

@Service
public class BookFacadeV2 {
    private final BookService bookService;

    public BookFacadeV2(BookService bookService) {
        this.bookService = bookService;
    }

    public BookResponseV2 createBook(BookRequestV1 bookRequestV1) {
        var book = bookService.createBook(bookRequestV1.title(),
                bookRequestV1.author(),
                bookRequestV1.isbn(),
                bookRequestV1.publishedYear());
        return BookResponseV2.fromBook(book);
    }

    public List<BookResponseV2> getAllBooks() {
        var books = bookService.getAllBooks();
        return books.stream()
                .map(BookResponseV2::fromBook)
                .toList();
    }

    public List<BookResponseV2> queryBooks(BookQuery query) {
        var books = bookService.queryBooks(query);
        return books.stream()
                .map(BookResponseV2::fromBook)
                .toList();
    }

    public BookResponseV2 getBookById(Long id) {
        var book = bookService.getBookById(id);
        return BookResponseV2.fromBook(book);
    }
}
