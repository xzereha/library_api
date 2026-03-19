package com.example.libraryapi.repository;

import com.example.libraryapi.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/** Repository interface for managing Book entities. */
@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    /**
     * Finds books by the author's name.
     *
     * @param authorName the name of the author
     * @return a list of books by the given author
     */
    List<Book> findByAuthorName(String authorName);
}
