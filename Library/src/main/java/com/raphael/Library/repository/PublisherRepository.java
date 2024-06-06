package com.raphael.Library.repository;

import com.raphael.Library.entities.books.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface PublisherRepository extends JpaRepository<Publisher, UUID> {

    @Query("SELECT p FROM publisher p WHERE p.name = :name")
    Optional<Publisher> findByName(@Param("name") String name);
}
