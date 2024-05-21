package com.raphael.Library.repository;

import com.raphael.Library.entities.books.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface AuthorRepository extends JpaRepository<Author, UUID> {

    @Query("SELECT s FROM author WHERE s.name = :name")
    Optional<Author> findByName(@Param("author") String author);
}
