package com.master.authservice.controller;

import com.master.authservice.domain.Institution;
import com.master.authservice.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public Institution update(
            @PathVariable UUID id,
            @RequestBody Institution role,
            @RequestHeader("Authorization") String token
            ) {
        return institutionService.update(id, role, token);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id, @RequestHeader("Authorization") String token) {
        institutionService.deleteById(id, token);
    }
}
