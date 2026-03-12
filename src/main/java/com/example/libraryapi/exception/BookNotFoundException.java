package com.example.libraryapi.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("Book with ID: " + id + " not found");
    }
}
