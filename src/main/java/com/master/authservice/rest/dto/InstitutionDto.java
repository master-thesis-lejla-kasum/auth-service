package com.master.authservice.rest.dto;

import com.master.authservice.domain.Institution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class InstitutionDto {
    private UUID id;
    private String identificationNumber;
    private String name;
    private String entity;
    private String canton;
    private String municipality;

    public InstitutionDto(Institution institution) {
        this.id = institution.getId();
        this.identificationNumber = institution.getIdentificationNumber();
        this.name = institution.getName();
        this.entity = institution.getEntity() != null ? institution.getEntity().getLabel() : null;
        this.canton =  institution.getCanton() != null ? institution.getCanton().getLabel() : null;
        this.municipality =  institution.getMunicipality() != null ? institution.getMunicipality().getLabel() : null;
    }
}
