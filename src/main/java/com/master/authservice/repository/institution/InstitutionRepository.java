package com.master.authservice.repository.institution;

import com.master.authservice.model.InstitutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstitutionRepository extends JpaRepository<InstitutionEntity, UUID>, InstitutionCustomRepository {
}
