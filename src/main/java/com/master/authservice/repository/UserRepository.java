package com.master.authservice.repository;

import com.master.authservice.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserAccount, UUID> {
}
