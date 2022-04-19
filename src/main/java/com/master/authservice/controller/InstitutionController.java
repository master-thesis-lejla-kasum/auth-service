package com.master.authservice.controller;

import com.master.authservice.domain.Institution;
import com.master.authservice.service.InstitutionService;
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
@RequestMapping("/api/v1/institution")
public class InstitutionController {
    @Autowired
    private InstitutionService institutionService;

    @GetMapping
    public List<Institution> getAll() {
        return institutionService.getAll();
    }

    @GetMapping("/{id}")
    public Institution getById(@PathVariable UUID id) {
        return institutionService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    public Institution create(@Valid @RequestBody Institution role) {
        return institutionService.add(role);
    }

    @PutMapping("/{id}")
    public Institution update(@PathVariable UUID id, @RequestBody Institution role) {
        return institutionService.update(id, role);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        institutionService.deleteById(id);
    }
}
