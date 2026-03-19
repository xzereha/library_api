package com.example.libraryapi.repository;

import com.example.libraryapi.model.Author;

import org.springframework.data.jpa.repository.JpaRepository;

/** Repository interface for managing Author entities. */
public interface AuthorRepository extends JpaRepository<Author, Long> {
    /**
     * Finds an author by their name.
     *
     * @param name the name of the author
     * @return the author with the given name, or null if not found
     */
    Author findByName(String name);
}
