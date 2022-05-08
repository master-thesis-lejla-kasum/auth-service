package com.master.authservice.controller;

import com.master.authservice.domain.AuthResponse;
import com.master.authservice.dto.LoginResponse;
import com.master.authservice.dto.LoginRequest;
import com.master.authservice.dto.ValidateRequest;
import com.master.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/validate")
    public void validate(@RequestBody ValidateRequest request) {
        authService.validateToken(request.getToken(), request.getRoles());
    }

    @PostMapping("/authenticate")
    public AuthResponse authenticate(@RequestParam String token) {
        return authService.authenticateWithToken(token);
    }
}
