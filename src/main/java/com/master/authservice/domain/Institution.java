package com.master.authservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Institution {
    private UUID id;
    @NotEmpty(message = "Identification number cannot be null or empty.")
    private String identificationNumber;
    @NotEmpty(message = "Name cannot be null or empty.")
    private String name;
    @NotNull(message = "Entity cannot be null or empty.")
    private Entity entity;
    private Canton canton;
    private Municipality municipality;
    private String address;
    private String phoneNumber;
    private boolean approved;
    @NotNull(message = "Account cannot be null.")
    private Account account;
}
