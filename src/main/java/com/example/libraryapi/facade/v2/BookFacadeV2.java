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
        var bookResponseV1 = bookFacadeV1.createBook(new com.example.libraryapi.dto.v1.BookRequestV1(
                bookRequestV1.title(), bookRequestV1.author(), bookRequestV1.isbn(), bookRequestV1.publishedYear()));
        return new BookResponseV2(bookResponseV1.id(), bookResponseV1.title(), bookResponseV1.author(),
                bookResponseV1.isbn(), bookResponseV1.publishedYear(), false);
    }

    public List<BookResponseV2> getAllBooks() {
        var books = bookService.getAllBooks();
        return books.stream()
                .map(book -> new BookResponseV2(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn(),
                        book.getPublishedYear(), false))
                .toList();
    }

    public BookResponseV2 getBookById(Long id) {
        var bookResponseV1 = bookFacadeV1.getBookById(id);
        return new BookResponseV2(bookResponseV1.id(), bookResponseV1.title(), bookResponseV1.author(),
                bookResponseV1.isbn(), bookResponseV1.publishedYear(), false);
    }
}
