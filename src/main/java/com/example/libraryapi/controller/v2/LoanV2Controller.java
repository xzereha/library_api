package com.example.libraryapi.controller.v2;

import com.example.libraryapi.dto.ResponseWrapper;
import com.example.libraryapi.dto.v2.LoanResponseV2;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Controller for managing loans in API version 2. */
@RestController
@RequestMapping("/api/v2/loans")
public class LoanV2Controller {

    /**
     * Endpoint for creating a new loan. This endpoint will handle the logic for creating a new loan
     * based on the provided request data. It will return a ResponseEntity containing the created
     * loan wrapped in a ResponseWrapper with metadata about the API version. If the book is not
     * available for loaning, it will return an 400 error response. If the book is missing then a
     * 404 is returned instead.
     *
     * @return a ResponseEntity containing the created loan wrapped in a ResponseWrapper with API
     *     version metadata, or an error response if the book is not available or missing
     */
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<LoanResponseV2>> createLoan() {
        // Implementation for creating a new loan
        return null; // Placeholder for actual implementation
    }

    @GetMapping()
    public ResponseEntity<ResponseWrapper<List<LoanResponseV2>>> getAllLoans() {
        // Implementation for retrieving all loans
        return null; // Placeholder for actual implementation
    }
}
