package com.example.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.libraryapi.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByName(String name);
}
