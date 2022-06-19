package com.master.authservice.model;

import com.master.authservice.domain.Account;
import com.master.authservice.domain.Canton;
import com.master.authservice.domain.Institution;
import com.master.authservice.domain.Municipality;
import com.master.authservice.dto.InstitutionResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
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

    public Institution toDomain() {
        Institution institution = new Institution();
        institution.setId(this.getId());
        institution.setIdentificationNumber(this.getIdentificationNumber());
        institution.setName(this.getName());
        institution.setEntity(this.getEntity());
        institution.setCanton(this.getCanton());
        institution.setMunicipality(this.getMunicipality());
        institution.setAddress(this.getAddress());
        institution.setPhoneNumber(this.getPhoneNumber());
        institution.setApproved(this.isApproved());

        Account account = new Account();
        account.setId(this.user.getId());
        account.setName(this.user.getName());
        account.setSurname(this.user.getSurname());
        account.setEmail(this.user.getEmail());

        institution.setAccount(account);

        return institution;
    }

    public InstitutionResponseDto toDto() {
        InstitutionResponseDto institution = new InstitutionResponseDto();
        institution.setId(this.getId());
        institution.setIdentificationNumber(this.getIdentificationNumber());
        institution.setName(this.getName());
        institution.setEntity(this.getEntity() != null ? this.getEntity().name() : "");
        institution.setCanton(this.getCanton() != null ? this.getCanton().name() : "");
        institution.setMunicipality(this.getMunicipality() != null ? this.getMunicipality().getLabel() : "");
        institution.setAddress(this.getAddress());
        institution.setPhoneNumber(this.getPhoneNumber());
        institution.setApproved(this.isApproved());

        Account account = new Account();
        account.setId(this.user.getId());
        account.setName(this.user.getName());
        account.setSurname(this.user.getSurname());
        account.setEmail(this.user.getEmail());

        institution.setAccount(account);

        return institution;
    }
}
