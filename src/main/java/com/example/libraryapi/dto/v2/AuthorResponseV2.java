package com.example.libraryapi.dto.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;

@Schema(description = "Response payload for an author")
public record AuthorResponseV2(
        @Nonnull @Schema(description = "Unique identifier of the author",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED,
                nullable = false) Long id,
        @Nonnull @Schema(description = "Name of the author",
                example = "J.R.R. Tolkien",
                requiredMode = Schema.RequiredMode.REQUIRED,
                nullable = false) String name) {

}
