package com.example.libraryapi.controller.v2;

import com.example.libraryapi.dto.ResponseWrapper;
import com.example.libraryapi.dto.v2.LoanRequestV2;
import com.example.libraryapi.dto.v2.LoanResponseV2;
import com.example.libraryapi.service.LoanService;

import jakarta.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/** Controller for managing loans in API version 2. */
@RestController
@RequestMapping("/api/v2/loans")
public class LoanV2Controller {

    private final LoanService loanService;

    /**
     * Constructor for LoanV2Controller. This constructor is used to inject the LoanService
     * dependency into the controller. The LoanService is used to handle business logic related to
     * loans, such as creating new loans and retrieving existing loans from the database.
     *
     * @param loanService the service for managing loans, injected by Spring's dependency injection
     *     mechanism
     */
    public LoanV2Controller(LoanService loanService) {
        this.loanService = loanService;
    }

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
    public ResponseEntity<ResponseWrapper<LoanResponseV2>> createLoan(
            @Valid @RequestBody final LoanRequestV2 loanRequest) {
        var loan = loanService.createLoan(loanRequest.bookId());
        var response = new ResponseWrapper<>(LoanResponseV2.fromLoan(loan), 2);
        var location = URI.create("/api/v2/loans/" + loan.getId());
        return ResponseEntity.created(location).body(response);
    }

    /**
     * Endpoint for retrieving all loans. This endpoint will return a list of all existing loans in
     * the system, wrapped in a ResponseWrapper with metadata about the API version. Each loan will
     * be represented as a LoanResponseV2 DTO containing the loan details.
     *
     * @return a ResponseEntity containing a list of all loans wrapped in a ResponseWrapper with API
     *     version metadata
     */
    @GetMapping()
    public ResponseEntity<ResponseWrapper<List<LoanResponseV2>>> getAllLoans() {
        var loans = loanService.getAllLoans();
        var dtos = loans.stream().map(LoanResponseV2::fromLoan).toList();
        var response = new ResponseWrapper<>(dtos, 2);
        return ResponseEntity.ok(response);
    }
}
