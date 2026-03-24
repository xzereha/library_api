package com.example.libraryapi.model;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

/** Represents a loan of a book in the library system. */
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @Nonnull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Book book;

    @Nonnull
    @ColumnDefault("CURRENT_DATE")
    private Date loanDate;

    @Nullable
    @ColumnDefault("NULL")
    private Date returnDate;

    /** Default constructor for JPA. Should not be used directly. */
    public Loan() {
        // Default constructor for JPA
    }

    /**
     * Constructor for creating a new Loan. This constructor is used to create a new loan for a
     * book. It takes a Book object and a Date object representing the loan date. The return date is
     * initialized to null, indicating that the book has not yet been returned.
     *
     * @param book the Book being loaned, must not be null
     * @param loanDate the date the loan was created, must not be null
     * @throws IllegalArgumentException if book or loanDate is null
     */
    public Loan(Book book, Date loanDate) {
        if (book == null || loanDate == null) {
            throw new IllegalArgumentException("Book and loanDate must not be null");
        }

        this.book = book;
        this.loanDate = loanDate;
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public String getTitle() {
        return book.getTitle();
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }
}
