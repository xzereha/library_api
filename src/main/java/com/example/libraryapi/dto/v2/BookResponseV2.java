package com.example.libraryapi.dto.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Response payload for book details in API version 2")
public class BookResponseV2 {
    @Nonnull
    @Schema(description = "The unique identifier of the book", example = "1", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private Long id;

    @NotBlank
    @Schema(description = "The title of the book", example = "The Lord of the Rings", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String title;

    @NotBlank
    @Schema(description = "The author of the book", example = "J.R.R. Tolkien", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private String author;

    @Nullable
    @Schema(description = "The ISBN of the book", example = "978-0261102385", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
    private String isbn;

    @Nullable
    @Schema(description = "The year the book was published", example = "1954", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
    private Integer publishedYear;

    @Nullable
    @Schema(description = "Whether the book is currently available for borrowing", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
    private Boolean available;

    public BookResponseV2() {}

    public BookResponseV2(@Nonnull Long id, @NotBlank String title, @NotBlank String author, @Nullable String isbn, @Nullable Integer publishedYear, @Nullable Boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(@Nonnull Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(@NotBlank String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(@Nullable String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(@Nullable Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(@Nullable Boolean available) {
        this.available = available;
    }
}
