package com.example.libraryapi.dto.v1;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload for creating a new book")
public record BookRequestV1(
        @NotBlank(message = "Title is required") @Schema(description = "The title of the book",
                example = "The Lord of the Rings",
                requiredMode = Schema.RequiredMode.REQUIRED,
                nullable = false) @Parameter(description = "The title of the book",
                        example = "The Lord of the Rings",
                        required = true) String title,

        @NotBlank(message = "Author is required") @Schema(description = "The author of the book",
                example = "J.R.R. Tolkien",
                requiredMode = Schema.RequiredMode.REQUIRED,
                nullable = false) @Parameter(description = "The author of the book",
                        example = "J.R.R. Tolkien",
                        required = true) String author,

        @Nullable @Schema(description = "The ISBN of the book",
                example = "978-0261102385",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                nullable = true) @Parameter(description = "The ISBN of the book",
                        example = "978-0261102385",
                        required = false) String isbn,

        @Nullable @Schema(description = "The year the book was published",
                example = "1954",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                nullable = true) @Parameter(description = "The year the book was published",
                        example = "1954",
                        required = false) Integer publishedYear) {
}
