package com.example.libraryapi.dto.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request payload for creating a new author")
public record AuthorRequestV2(
        @NotBlank(message = "Name is required") @Schema(description = "Name of the author",
                example = "J.R.R. Tolkien",
                requiredMode = Schema.RequiredMode.REQUIRED,
                nullable = false) String name) {

}
