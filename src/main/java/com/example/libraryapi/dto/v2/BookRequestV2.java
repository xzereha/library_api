package com.example.libraryapi.dto.v2;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.jspecify.annotations.Nullable;

/**
 * DTO for creating a new book (version 2).
 *
 * @param title The title of the book
 * @param authorId The ID of the author of the book
 * @param isbn The ISBN of the book (optional)
 * @param publishedYear The year the book was published (optional)
 */
@Schema(description = "Request payload for creating a new book")
public record BookRequestV2(
        @NotBlank(message = "Title is required")
                @Schema(
                        description = "The title of the book",
                        example = "The Lord of the Rings",
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        nullable = false)
                @Parameter(
                        description = "The title of the book",
                        example = "The Lord of the Rings",
                        required = true)
                String title,
        @NotNull(message = "Author is required")
                @Schema(
                        description = "The ID of the author of the book",
                        example = "1",
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        nullable = false)
                @Parameter(
                        description = "The ID of the author of the book",
                        example = "1",
                        required = true)
                Long authorId,
        @Nullable
                @Schema(
                        description = "The ISBN of the book",
                        example = "978-0261102385",
                        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                        nullable = true)
                @Parameter(
                        description = "The ISBN of the book",
                        example = "978-0261102385",
                        required = false)
                String isbn,
        @Nullable
                @Schema(
                        description = "The year the book was published",
                        example = "1954",
                        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                        nullable = true)
                @Parameter(
                        description = "The year the book was published",
                        example = "1954",
                        required = false)
                Integer publishedYear) {}
