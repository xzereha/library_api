package com.example.libraryapi.facade.v2;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.libraryapi.dto.v1.BookRequestV1;
import com.example.libraryapi.dto.v2.BookResponseV2;
import com.example.libraryapi.facade.v1.BookFacadeV1;
import com.example.libraryapi.service.BookService;

@Service
public class BookFacadeV2 {
    private final BookFacadeV1 bookFacadeV1;
    private final BookService bookService;


    public BookFacadeV2(BookFacadeV1 bookFacadeV1, BookService bookService) {
        this.bookFacadeV1 = bookFacadeV1;
        this.bookService = bookService;
    }

    public BookResponseV2 createBook(BookRequestV1 bookRequestV1) {
        var bookResponseV1 = bookFacadeV1.createBook(new com.example.libraryapi.dto.v1.BookRequestV1(bookRequestV1.getTitle(), bookRequestV1.getAuthor(), bookRequestV1.getIsbn(), bookRequestV1.getPublishedYear()));
        return new BookResponseV2(bookResponseV1.getId(), bookResponseV1.getTitle(), bookResponseV1.getAuthor(), bookResponseV1.getIsbn(), bookResponseV1.getPublishedYear(), false);
    }

    public List<BookResponseV2> getAllBooks() {
        var books = bookService.getAllBooks();
        return books.stream()
            .map(book -> new BookResponseV2(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishedYear(), false))
            .toList();
    }

    public BookResponseV2 getBookById(Long id) {
        var bookResponseV1 = bookFacadeV1.getBookById(id);
        return new BookResponseV2(bookResponseV1.getId(), bookResponseV1.getTitle(), bookResponseV1.getAuthor(), bookResponseV1.getIsbn(), bookResponseV1.getPublishedYear(), false);
    }
}
