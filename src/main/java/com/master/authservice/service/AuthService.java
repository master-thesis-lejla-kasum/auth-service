package com.master.authservice.service;

import com.master.authservice.domain.AuthResponse;
import com.master.authservice.dto.LoginResponse;
import com.master.authservice.dto.LoginRequest;
import com.master.authservice.exceptions.AccessForbiddenException;
import com.master.authservice.exceptions.AccessUnauthorizedException;
import com.master.authservice.model.RoleEntity;
import com.master.authservice.model.UserAccountEntity;
import com.master.authservice.repository.UserRepository;
import com.master.authservice.security.CustomUserDetails;
import com.master.authservice.util.JwtUtil;
import com.master.authservice.security.RepositoryAwareUserDetailsService;
import com.master.authservice.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    UserRepository userRepository;

    private final AuthenticationManager authenticationManager;
    private final RepositoryAwareUserDetailsService userDetailsService;

    private final String SECRET_KEY;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       RepositoryAwareUserDetailsService userDetailsService,
                       @Value("${secret-key}") String SECRET_KEY) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.SECRET_KEY = SECRET_KEY;
    }

    public void validateToken(String token, List<String> roles) {
        boolean isTokenValid = true;
        String username = "";
        try {
            username = JwtUtil.extractUsername(token, SECRET_KEY);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            isTokenValid = JwtUtil.validateToken(token, userDetails, SECRET_KEY);
        } catch (Exception ex) {
            LOGGER.error("Unable to validate token", ex);
            isTokenValid = false;
        }
        if (!isTokenValid) {
            throw new AccessUnauthorizedException("Access unauthorized. Invalid token.");
        }

        UserAccountEntity user = userRepository.findByEmail(username);
        List<String> userRoles = user.getRoles().stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toList());

        boolean hasSameAuthorities = roles.containsAll(userRoles) && userRoles.containsAll(roles);
        if (!hasSameAuthorities) {
            throw new AccessForbiddenException("Access forbidden.");
        }
    }

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new AccessUnauthorizedException("Incorrect username or password");
        }

        final CustomUserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        final String token = JwtUtil.generateToken(userDetails, SECRET_KEY, false);

        return new LoginResponse(token);
    }

    public AuthResponse authenticateWithToken(String token) {
        try {
            String email = JwtUtil.extractUsername(token, SECRET_KEY);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (!JwtUtil.validateToken(token, userDetails, SECRET_KEY)) {
                throw new AccessUnauthorizedException("Invalid token.");
            }

            UserAccountEntity account = userRepository.findByEmail(email);
            List<SimpleGrantedAuthority> authorities = account.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
            User user = new User(account.getEmail(), account.getPassword(), authorities);

            List<String> userAuthorities = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return new AuthResponse(user.getUsername(), user.getPassword(), userAuthorities);
        } catch (Exception e) {
            throw new AccessUnauthorizedException("Invalid token.");
        }


    }

    private String hashPassword(String password) {
        return PasswordUtil.hashPassword(password);
    }

}
