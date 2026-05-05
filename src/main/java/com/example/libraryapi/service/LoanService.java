package com.example.libraryapi.service;

import com.example.libraryapi.exception.BookNotAvailableException;
import com.example.libraryapi.exception.BookNotFoundException;
import com.example.libraryapi.model.Loan;
import com.example.libraryapi.repository.BookRepository;
import com.example.libraryapi.repository.LoanRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/** Service layer for managing loans. */
@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    /**
     * Constructor for LoanService. This constructor is used to inject the LoanRepository dependency
     * into the service. The LoanRepository is used to perform CRUD operations on Loan entities in
     * the database. The BookService is used to manage book-related operations.
     *
     * @param loanRepository the repository for managing Loan entities, injected by Spring's
     *     dependency injection mechanism
     * @param bookRepository the repository for managing Book entities, injected by Spring's
     *     dependency injection mechanism
     */
    public LoanService(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    /**
     * Creates a new loan for the book with the specified ID. This method will check if the book is
     * available for loaning, and if so, it will create a new Loan entity and save it to the
     * database. If the book is not available, it will throw a BookNotAvailableException. If the
     * book is missing, it will throw a BookNotFoundException.
     *
     * @param bookId the unique identifier of the book to be loaned
     * @return the created Loan entity
     * @throws BookNotAvailableException if the book is not available for loaning
     * @throws BookNotFoundException if the book with the specified ID does not exist
     */
    @Transactional
    public Loan createLoan(Long bookId) {
        var book =
                bookRepository
                        .findByIdForUpdate(bookId)
                        .orElseThrow(() -> new BookNotFoundException(bookId));
        if (!book.isAvailable()) {
            throw new BookNotAvailableException(bookId);
        }
        var loan = new Loan(book, new Date());
        return loanRepository.save(loan);
    }
}
