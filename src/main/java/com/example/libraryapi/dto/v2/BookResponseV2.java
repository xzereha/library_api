package com.example.libraryapi.dto.v2;

import com.example.libraryapi.model.Book;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for book details response (version 2).
 *
 * <p>This version includes an additional field for book availability status.
 */
@Schema(description = "Response payload for book details in API version 2")
public record BookResponseV2(
        @Nonnull
                @Schema(
                        description = "The unique identifier of the book",
                        example = "1",
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        nullable = false)
                Long id,
        @NotBlank
                @Schema(
                        description = "The title of the book",
                        example = "The Lord of the Rings",
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        nullable = false)
                String title,
        @NotBlank
                @Schema(
                        description = "The author of the book",
                        example = "J.R.R. Tolkien",
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        nullable = false)
                String author,
        @Nullable
                @Schema(
                        description = "The ISBN of the book",
                        example = "978-0261102385",
                        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                        nullable = true)
                String isbn,
        @Nullable
                @Schema(
                        description = "The year the book was published",
                        example = "1954",
                        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                        nullable = true)
                Integer publishedYear,
        @Nullable
                @Schema(
                        description = "Is the book currently available for borrowing",
                        example = "true",
                        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                        nullable = true)
                Boolean available) {

    /**
     * Factory method to create a BookResponseV2 from a Book entity.
     *
     * @param book The Book entity to convert
     * @return A BookResponseV2 instance populated with data from the Book entity
     */
    public static BookResponseV2 fromBook(Book book) {
        return new BookResponseV2(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getName(),
                book.getIsbn(),
                book.getPublishedYear(),
                book.isAvailable());
    }
}
