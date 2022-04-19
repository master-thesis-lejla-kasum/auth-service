package com.master.authservice.controller;

import com.master.authservice.domain.Account;
import com.master.authservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAll() {
        return accountService.getAll();
    }

    @GetMapping("/{id}")
    public Account getById(@PathVariable UUID id) {
        return accountService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    public Account create(@Valid @RequestBody Account role) {
        return accountService.add(role);
    }

    @PutMapping("/{id}")
    public Account update(@PathVariable UUID id, @RequestBody Account role) {
        return accountService.update(id, role);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        accountService.deleteById(id);
    }
}
