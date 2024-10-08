package com.raphael.Library.repository;

import com.raphael.Library.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM book b WHERE b.name = :name")
    Optional<Book> findByBookName(@Param("name") String name);

    @Query("SELECT b FROM book b WHERE b.author = :author")
    List<Book> findByAuthorName(@Param("author") String authorName);

    @Query("SELECT b FROM book b WHERE b.publisher = :publisher")
    List<Book> findByPublisherName(@Param("publisher") String publisherName);
}
