package com.master.authservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Account {
    private UUID id;
    @NotEmpty(message = "Name cannot be null or empty.")
    private String name;
    @NotEmpty(message = "Surname cannot be null or empty.")
    private String surname;
    @NotEmpty(message = "Email cannot be null or empty.")
    @Email
    private String email;
    private String password;
    private Institution institution;
    private List<Role> roles;
}
