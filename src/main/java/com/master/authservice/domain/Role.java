package com.master.authservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Role {
    private UUID id;

    @NotEmpty(message = "Name cannot be null or empty.")
    private String name;
}
