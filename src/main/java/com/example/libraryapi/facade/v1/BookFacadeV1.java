package com.example.libraryapi.facade.v1;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.libraryapi.dto.v1.BookRequestV1;
import com.example.libraryapi.dto.v1.BookResponseV1;
import com.example.libraryapi.service.BookService;

@Service
public class BookFacadeV1 {
    private final BookService bookService;

    public BookFacadeV1(BookService bookService) {
        this.bookService = bookService;
    }

    public BookResponseV1 createBook(BookRequestV1 bookRequest) {
        var book = bookService.createBook(bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.getIsbn(), bookRequest.getPublishedYear());
        return new BookResponseV1(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishedYear());
    }

    public List<BookResponseV1> getAllBooks() {
        var books = bookService.getAllBooks();
        return books.stream()
            .map(book -> new BookResponseV1(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishedYear()))
            .toList();
    }
}
