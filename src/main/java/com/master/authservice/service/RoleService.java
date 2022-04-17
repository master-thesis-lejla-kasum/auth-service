package com.master.authservice.service;

import com.master.authservice.domain.Role;
import com.master.authservice.exceptions.EntityNotFoundException;
import com.master.authservice.model.RoleEntity;
import com.master.authservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public List<Role> getAll() {
        return roleRepository.findAll().stream()
                .map(RoleEntity::toDomain)
                .collect(Collectors.toList());
    }

    public Role getById(UUID id) {
        if (!roleRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Role with id=%s not found.", id));
        }
        return roleRepository.findById(id).map(RoleEntity::toDomain).get();
    }

    public Role add(Role role) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(role.getName());

        return roleRepository.save(roleEntity).toDomain();
    }

    public Role update(UUID id, Role role) {
        Optional<RoleEntity> roleEntity = roleRepository.findById(id);
        if (!roleEntity.isPresent()) {
            throw new EntityNotFoundException(String.format("Role with id=%s not found.", id));
        }

        RoleEntity entity = roleEntity.get();
        entity.setName(role.getName());

        return roleRepository.save(entity).toDomain();
    }

    public void deleteById(UUID id) {
        if (!roleRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Role with id=%s not found.", id));
        }
        roleRepository.deleteById(id);
    }
}
