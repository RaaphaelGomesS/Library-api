package com.raphael.Library.repository;

import com.raphael.Library.entities.Requisition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface RequisitionRepository extends JpaRepository<Requisition, UUID> {
}
