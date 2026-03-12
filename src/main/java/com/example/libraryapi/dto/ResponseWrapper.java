package com.example.libraryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;

@Schema(description = "Wrapper for API responses, including data and version information")
public record ResponseWrapper<T>(
        @Nonnull @Schema(description = "Data of the response",
                requiredMode = Schema.RequiredMode.REQUIRED,
                nullable = false) T data,

        @Min(1) @Schema(description = "API version",
                requiredMode = Schema.RequiredMode.REQUIRED,
                nullable = false) int version) {
}
