package com.example.libraryapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.libraryapi.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    // TODO: Names are not unique so this should return a list,
    // but for simplicity we assume that they are unique for now
    List<Book> findByAuthor(String string);

}
