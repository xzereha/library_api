package com.example.libraryapi.repository;

import com.example.libraryapi.model.Book;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    /**
     * Load a Book by id and acquire a pessimistic write lock (for update). Used to ensure only one
     * transaction can create a loan for the book at a time.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Book b where b.id = :id")
    Optional<Book> findByIdForUpdate(@Param("id") Long id);
}
