package com.master.authservice.repository;

import com.master.authservice.model.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserAccountEntity, UUID> {
}
