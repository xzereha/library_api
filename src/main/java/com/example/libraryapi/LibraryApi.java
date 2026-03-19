package com.example.libraryapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Core application class for the LibraryAPI Spring Boot application. This class serves as the entry
 * point for the application.
 */
@SpringBootApplication
public class LibraryApi {

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        SpringApplication.run(LibraryApi.class, args);
    }
}
