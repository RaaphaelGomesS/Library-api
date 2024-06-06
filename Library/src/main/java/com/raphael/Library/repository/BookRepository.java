package com.raphael.Library.repository;

import com.raphael.Library.entities.books.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface BookRepository extends JpaRepository<Book, UUID> {

    @Query("SELECT b FROM book b WHERE b.name = :name")
    Optional<Book> findByName(@Param("name") String name);
}
