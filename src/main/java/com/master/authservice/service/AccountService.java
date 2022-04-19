package com.master.authservice.service;

import com.master.authservice.domain.Account;
import com.master.authservice.domain.Role;
import com.master.authservice.exceptions.EntityNotFoundException;
import com.master.authservice.model.RoleEntity;
import com.master.authservice.model.UserAccountEntity;
import com.master.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    UserRepository userRepository;

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
        entity.setPassword(account.getPassword());

        List<RoleEntity> roles = domainRolesToEntities(account.getRoles());

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

        List<RoleEntity> roles = domainRolesToEntities(account.getRoles());

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
}
