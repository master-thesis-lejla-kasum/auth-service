package com.master.authservice.security;

import com.master.authservice.domain.Account;
import com.master.authservice.model.UserAccountEntity;
import com.master.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RepositoryAwareUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public RepositoryAwareUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccountEntity userAccount = repository.findByEmail(email);

        List<SimpleGrantedAuthority> authorities = userAccount.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new CustomUserDetails(userAccount.getEmail(), userAccount.getPassword(), authorities, userAccount.getId());
    }
}
