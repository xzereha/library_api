package com.example.libraryapi.dto;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class BookResponse {
    @Nonnull
    private Long id;
    @Nonnull
    private String title;
    @Nonnull
    private String author;
    @Nullable
    private String isbn;
    private int publishedYear;

    public BookResponse() {
    }

    public BookResponse(@Nonnull Long id, @Nonnull String title, @Nonnull String author, @Nullable String isbn, int publishedYear) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
    }

    public @Nonnull Long getId() {
        return id;
    }

    public void setId(@Nonnull Long id) {
        this.id = id;
    }

    public @Nonnull String getTitle() {
        return title;
    }

    public void setTitle(@Nonnull String title) {
        this.title = title;
    }

    public @Nonnull String getAuthor() {
        return author;
    }

    public void setAuthor(@Nonnull String author) {
        this.author = author;
    }

    public @Nullable String getIsbn() {
        return isbn;
    }

    public void setIsbn(@Nullable String isbn) {
        this.isbn = isbn;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }
}
