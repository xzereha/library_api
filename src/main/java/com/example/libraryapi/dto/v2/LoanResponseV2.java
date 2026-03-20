package com.example.libraryapi.dto.v2;

import com.example.libraryapi.model.Loan;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

/** DTO for loan details response (version 2). */
@Schema(description = "Response payload for loan details in API version 2")
public record LoanResponseV2(
        @Nonnull
                @Schema(
                        description = "The unique identifier of the loan",
                        example = "1",
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        nullable = false)
                Long id,
        @Nonnull
                @Schema(
                        description = "The unique identifier of the book",
                        example = "1",
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        nullable = false)
                Long bookId,
        @NotBlank
                @Schema(
                        description = "The title of the book",
                        example = "Effective Java",
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        nullable = false)
                String title,
        @Nonnull
                @Schema(
                        description = "The date when the loan was made",
                        example = "2023-01-01",
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        nullable = false)
                Date loanDate,
        @Nullable
                @Schema(
                        description =
                                "The date when the loan was returned (null if not yet returned)",
                        example = "2023-01-15",
                        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                        nullable = true)
                Date returnDate) {
    /**
     * Factory method to create a LoanResponseV2 from a Loan entity.
     *
     * @param loan The Loan entity to convert
     * @return A LoanResponseV2 instance containing the loan details
     */
    public static LoanResponseV2 fromLoan(Loan loan) {
        return new LoanResponseV2(
                loan.getId(),
                loan.getBook().getId(),
                loan.getTitle(),
                loan.getLoanDate(),
                loan.getReturnDate());
    }
}
