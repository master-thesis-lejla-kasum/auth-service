package com.master.authservice.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Account {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private Institution institution;
}
