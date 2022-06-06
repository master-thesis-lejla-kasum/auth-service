package com.master.authservice.service;

import com.master.authservice.domain.Account;
import com.master.authservice.domain.Role;
import com.master.authservice.exceptions.BadRequestException;
import com.master.authservice.exceptions.EntityNotFoundException;
import com.master.authservice.model.RoleEntity;
import com.master.authservice.model.UserAccountEntity;
import com.master.authservice.repository.RoleRepository;
import com.master.authservice.repository.UserRepository;
import com.master.authservice.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public List<Account> getAll() {
        return userRepository.findAll().stream()
                .map(UserAccountEntity::toDomain)
                .collect(Collectors.toList());
    }

    public Account getById(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Account with id=%s does not exists.", id));
        }
        return userRepository.findById(id).get().toDomain();
    }

    public Account add(Account account) {
        UserAccountEntity entity = new UserAccountEntity();
        entity.setName(account.getName());
        entity.setSurname(account.getSurname());
        entity.setEmail(account.getEmail());
        entity.setPassword(PasswordUtil.hashPassword(account.getPassword()));

        checkRolesExist(account.getRoles() != null ? account.getRoles() : new ArrayList<>());

        List<RoleEntity> roles = domainRolesToEntities(account.getRoles() != null ? account.getRoles() : new ArrayList<>());

        entity.setRoles(roles);

        return userRepository.save(entity).toDomain();
    }

    public Account update(UUID id, Account account) {
        Optional<UserAccountEntity> userEntity = userRepository.findById(id);
        if (!userEntity.isPresent()) {
            throw new EntityNotFoundException(String.format("Account with id=%s does not exists.", id));
        }

        UserAccountEntity entity = userEntity.get();
        entity.setName(account.getName());
        entity.setSurname(account.getSurname());
        entity.setEmail(account.getEmail());
        entity.setPassword(account.getPassword());

        checkRolesExist(account.getRoles() != null ? account.getRoles() : new ArrayList<>());

        List<RoleEntity> roles = domainRolesToEntities(account.getRoles() != null ? account.getRoles() : new ArrayList<>());

        entity.setRoles(roles);

        return userRepository.save(entity).toDomain();
    }

    public void deleteById(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Account with id=%s does not exists.", id));
        }
        userRepository.deleteById(id);
    }

    private List<RoleEntity> domainRolesToEntities(List<Role> roles) {
        return roles.stream()
                .map(role -> {
                    RoleEntity roleEntity = new RoleEntity();
                    roleEntity.setId(role.getId());
                    return roleEntity;
                })
                .collect(Collectors.toList());
    }

    private void checkRolesExist(List<Role> roles) {
        roles.forEach(role -> {
            if (!roleRepository.existsById(role.getId())) {
                throw new  BadRequestException(String.format("Role with id=%s does not exists.", role.getId()));
            }
        });
    }
}
