package com.master.authservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ValidateRequest {
    private String token;
    private List<String> roles;
}
