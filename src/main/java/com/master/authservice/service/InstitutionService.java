package com.master.authservice.service;

import com.master.authservice.domain.Institution;
import com.master.authservice.exceptions.BadRequestException;
import com.master.authservice.exceptions.EntityNotFoundException;
import com.master.authservice.model.InstitutionEntity;
import com.master.authservice.model.UserAccountEntity;
import com.master.authservice.repository.InstitutionRepository;
import com.master.authservice.repository.UserRepository;
import com.master.authservice.rest.CovidServiceClient;
import com.master.authservice.rest.dto.InstitutionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class InstitutionService {
    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CovidServiceClient covidClient;

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

        Institution created = institutionRepository.save(entity).toDomain();

        createInstitutionInCovidService(created);

        return created;
    }

    public Institution update(UUID id, Institution institution, String token) {
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

        updateInstitutionInCovidService(institution, id, token);

        return institutionRepository.save(entity).toDomain();
    }

    public void deleteById(UUID id, String token) {
        if (!institutionRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Institution with id=%s does not exist.", id));
        }

        deleteInstitutionInCovidService(id.toString(), token);
        institutionRepository.deleteById(id);
    }

    private void checkAccountExists(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new BadRequestException(String.format("Account with id=%s does not exists.", id));
        }
    }

    private void createInstitutionInCovidService(Institution institution) {
        InstitutionDto dto = new InstitutionDto(institution);

        covidClient.addInstitution(dto);
    }

    private void updateInstitutionInCovidService(Institution institution, UUID id, String token) {
        InstitutionDto dto = new InstitutionDto(institution);
        dto.setId(id);

        covidClient.updateInstitution(dto, id.toString(), token);
    }

    private void deleteInstitutionInCovidService(String id, String token) {
        covidClient.deleteInstitution(id, token);
    }
}
