package com.example.libraryapi.dto.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

@Schema(description = "Response payload for book details")
public record BookResponseV1(
    @Nonnull
    @Schema(description = "The unique identifier of the book", example = "1", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    Long id,

    @Nonnull
    @Schema(description = "The title of the book", example = "The Lord of the Rings", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    String title,

    @Nonnull
    @Schema(description = "The author of the book", example = "J.R.R. Tolkien", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    String author,

    @Nullable
    @Schema(description = "The ISBN of the book", example = "978-0261102385", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
    String isbn,

    @Nullable
    @Schema(description = "The year the book was published", example = "1954", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
    Integer publishedYear
) { }
