package com.master.authservice.dto;

import com.master.authservice.domain.Account;
import com.master.authservice.domain.Canton;
import com.master.authservice.domain.Entity;
import com.master.authservice.domain.Municipality;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionResponseDto {
    private UUID id;
    private String identificationNumber;
    private String name;
    private String entity;
    private String canton;
    private String municipality;
    private String address;
    private String phoneNumber;
    private boolean approved;
    private Account account;
}
