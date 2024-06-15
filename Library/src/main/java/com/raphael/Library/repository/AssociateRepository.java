package com.raphael.Library.repository;

import com.raphael.Library.entities.Associate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface AssociateRepository extends JpaRepository<Associate, Long> {

    @Query("SELECT a FROM associate a WHERE a.email = :email")
    Optional<Associate> findByEmail(@Param("email") String email);
}
