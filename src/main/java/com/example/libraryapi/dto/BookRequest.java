package com.example.libraryapi.dto;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

public class BookRequest {
    @NotBlank(message = "Title is required")
    @Schema(description = "The title of the book", example = "The Lord of the Rings", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String title;
    @NotBlank(message = "Author is required")
    @Schema(description = "The author of the book", example = "J.R.R. Tolkien", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String author;
    @Nullable
    @Schema(description = "The ISBN of the book", example = "978-0261102385", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
    private String isbn;
    @Schema(description = "The year the book was published", example = "1954", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
    private int publishedYear;

    public BookRequest() {
        this("", "");
    }

    public BookRequest(@Nonnull String title, @Nonnull String author) {
        this(title, author, null, 0);
    }

    public BookRequest(@Nonnull String title, @Nonnull String author, @Nullable String isbn, int publishedYear) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
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

    public String getIsbn() {
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
