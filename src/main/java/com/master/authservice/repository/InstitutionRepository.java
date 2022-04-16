package com.master.authservice.repository;

import com.master.authservice.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstitutionRepository extends JpaRepository<Institution, UUID> {
}
