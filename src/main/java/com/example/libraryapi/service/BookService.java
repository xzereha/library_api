package com.example.libraryapi.service;

import java.util.List;

import com.example.libraryapi.exception.BookNotFoundException;
import com.example.libraryapi.model.Book;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

public interface BookService {
    @Nonnull
    Book createBook(
            @Nonnull @NotBlank String title,
            @Nonnull @NotBlank String author,
            String isbn,
            Integer publishedYear);

    @Nonnull
    List<Book> getAllBooks();

    @Nonnull
    Book getBookById(@Nonnull Long id) throws BookNotFoundException;
}
