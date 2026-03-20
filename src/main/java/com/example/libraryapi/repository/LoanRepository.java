package com.example.libraryapi.repository;

import com.example.libraryapi.model.Loan;

import org.springframework.data.jpa.repository.JpaRepository;

/** Repository interface for managing Loan entities. */
public interface LoanRepository extends JpaRepository<Loan, Long> {}
