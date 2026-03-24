package com.example.libraryapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.util.Date;

class LoanTest {
    @Test
    void testNullBookThrowsException() {
        Date loanDate = new Date();
        assertThrows(IllegalArgumentException.class, () -> new Loan(null, loanDate));
    }

    @Test
    void testNullLoanDateThrowsException() {
        Book book = new Book();
        assertThrows(IllegalArgumentException.class, () -> new Loan(book, null));
    }

    @Test
    void testValidLoanCreation() {
        Book book = new Book();
        Date loanDate = new Date();
        Loan loan = new Loan(book, loanDate);
        assertEquals(book, loan.getBook());
        assertEquals(loanDate, loan.getLoanDate());
        assertEquals(null, loan.getReturnDate());
    }
}
