package com.master.authservice.model;

import com.master.authservice.domain.Canton;
import com.master.authservice.domain.Municipality;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "institution")
@Getter
@Setter
public class InstitutionEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "identification_number")
    private String identificationNumber;

    private String name;

    private com.master.authservice.domain.Entity entity;

    private Canton canton;

    private Municipality municipality;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    private boolean approved;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserAccountEntity user;

    public InstitutionEntity(
            String identificationNumber,
            String name,
            com.master.authservice.domain.Entity entity,
            Canton canton,
            Municipality municipality,
            String address,
            String phoneNumber,
            boolean approved,
            UserAccountEntity user
    ) {
        this.identificationNumber = identificationNumber;
        this.name = name;
        this.entity = entity;
        this.canton = canton;
        this.municipality = municipality;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.approved = approved;
        this.user = user;
    }
}
