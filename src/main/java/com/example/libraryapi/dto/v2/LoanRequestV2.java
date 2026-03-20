package com.example.libraryapi.dto.v2;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.annotation.Nonnull;

/** DTO for loan request (version 2). */
@Schema(description = "Request payload for creating a new loan in API version 2")
public record LoanRequestV2(
        @Nonnull
                @Schema(
                        description = "The unique identifier of the book to be loaned",
                        example = "1",
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        nullable = false)
                Long bookId) {}
