package com.example.libraryapi.model;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

/** Represents a loan of a book in the library system. */
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne @Nonnull private Book book;

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

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
