package com.master.authservice.service;

import com.master.authservice.domain.Institution;
import com.master.authservice.exceptions.BadRequestException;
import com.master.authservice.exceptions.EntityNotFoundException;
import com.master.authservice.model.InstitutionEntity;
import com.master.authservice.model.UserAccountEntity;
import com.master.authservice.repository.InstitutionRepository;
import com.master.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InstitutionService {
    @Autowired
    InstitutionRepository institutionRepository;

    @Autowired
    UserRepository userRepository;

    public List<Institution> getAll() {
        return institutionRepository.findAll().stream()
                .map(InstitutionEntity::toDomain)
                .collect(Collectors.toList());
    }

    public Institution getById(UUID id) {
        if (!institutionRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Institution with id=%s does not exist.", id));
        }
        return institutionRepository.findById(id).get().toDomain();
    }

    public Institution add(Institution institution) {
        InstitutionEntity entity = new InstitutionEntity();
        entity.setIdentificationNumber(institution.getIdentificationNumber());
        entity.setName(institution.getName());
        entity.setEntity(institution.getEntity());
        entity.setCanton(institution.getCanton());
        entity.setMunicipality(institution.getMunicipality());
        entity.setAddress(institution.getAddress());
        entity.setPhoneNumber(institution.getPhoneNumber());
        entity.setApproved(institution.isApproved());

        checkAccountExists(institution.getAccount().getId());

        UserAccountEntity userEntity = new UserAccountEntity();
        userEntity.setId(institution.getAccount().getId());

        entity.setUser(userEntity);

        return institutionRepository.save(entity).toDomain();
    }

    public Institution update(UUID id, Institution institution) {
        Optional<InstitutionEntity> institutionEntity = institutionRepository.findById(id);

        if (!institutionEntity.isPresent()) {
            throw new EntityNotFoundException(String.format("Institution with id=%s does not exist.", id));
        }

        InstitutionEntity entity = institutionEntity.get();
        entity.setIdentificationNumber(institution.getIdentificationNumber());
        entity.setName(institution.getName());
        entity.setEntity(institution.getEntity());
        entity.setCanton(institution.getCanton());
        entity.setMunicipality(institution.getMunicipality());
        entity.setAddress(institution.getAddress());
        entity.setPhoneNumber(institution.getPhoneNumber());
        entity.setApproved(institution.isApproved());

        checkAccountExists(institution.getAccount().getId());

        UserAccountEntity userEntity = new UserAccountEntity();
        userEntity.setId(institution.getAccount().getId());

        entity.setUser(userEntity);

        return institutionRepository.save(entity).toDomain();
    }

    public void deleteById(UUID id) {
        if (!institutionRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Institution with id=%s does not exist.", id));
        }
        institutionRepository.deleteById(id);
    }

    private void checkAccountExists(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new BadRequestException(String.format("Account with id=%s does not exists.", id));
        }
    }
}
