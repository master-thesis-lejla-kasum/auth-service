package com.master.authservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.UUID;

@Getter
@Setter
public class Institution {
    private UUID id;
    private String identificationNumber;
    private String name;
    private Entity entity;
    private Canton canton;
    private Municipality municipality;
    private String address;
    private String phoneNumber;
    private boolean approved;
    private Account account;
}
