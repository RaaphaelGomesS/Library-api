package com.raphael.Library.repository;

import com.raphael.Library.entities.Associate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AssociateRepository extends JpaRepository<Associate, UUID> {

    @Query("SELECT a FROM associate WHERE a.email = :email")
    Optional<Associate> findByEmail(@Param("email") String email);
}
