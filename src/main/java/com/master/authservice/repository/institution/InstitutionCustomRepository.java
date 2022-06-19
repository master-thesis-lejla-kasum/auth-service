package com.master.authservice.repository.institution;

import com.master.authservice.dto.InstitutionSearchRequest;
import com.master.authservice.model.InstitutionEntity;

import java.util.List;

public interface InstitutionCustomRepository {

    List<InstitutionEntity> search(InstitutionSearchRequest request);
}
