package com.example.libraryapi.dto.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class BookResponseV1 {
    @Nonnull
    @Schema(description = "The unique identifier of the book", example = "1", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private Long id;
    
    @Nonnull
    @Schema(description = "The title of the book", example = "The Lord of the Rings", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String title;
    
    @Nonnull
    @Schema(description = "The author of the book", example = "J.R.R. Tolkien", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String author;

    @Nullable
    @Schema(description = "The ISBN of the book", example = "978-0261102385", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
    private String isbn;

    @Nullable
    @Schema(description = "The year the book was published", example = "1954", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
    private Integer publishedYear;

    public BookResponseV1() {}

    public BookResponseV1(@Nonnull Long id, @Nonnull String title, @Nonnull String author) {
        this(id, title, author, null, null);
    }

    public BookResponseV1(@Nonnull Long id, @Nonnull String title, @Nonnull String author, @Nullable String isbn, @Nullable Integer publishedYear) {
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

    public @Nullable Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(@Nullable Integer publishedYear) {
        this.publishedYear = publishedYear;
    }
}
