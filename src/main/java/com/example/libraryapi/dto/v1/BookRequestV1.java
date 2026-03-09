package com.example.libraryapi.dto.v1;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload for creating a new book")
public class BookRequestV1 {
    @NotBlank(message = "Title is required")
    @Schema(description = "The title of the book", example = "The Lord of the Rings", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    @Parameter(description = "The title of the book", example = "The Lord of the Rings", required = true)
    private String title;

    @NotBlank(message = "Author is required")
    @Schema(description = "The author of the book", example = "J.R.R. Tolkien", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    @Parameter(description = "The author of the book", example = "J.R.R. Tolkien", required = true)
    private String author;

    @Nullable
    @Schema(description = "The ISBN of the book", example = "978-0261102385", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
    @Parameter(description = "The ISBN of the book", example = "978-0261102385", required = false)
    private String isbn;
    
    @Nullable
    @Schema(description = "The year the book was published", example = "1954", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
    @Parameter(description = "The year the book was published", example = "1954", required = false)
    private Integer publishedYear;

    public BookRequestV1() {}

    public BookRequestV1(@Nonnull String title, @Nonnull String author) {
        this(title, author, null, null);
    }

    public BookRequestV1(@Nonnull String title, @Nonnull String author, @Nullable String isbn, @Nullable Integer publishedYear) {
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
