package com.master.authservice.model;

import com.master.authservice.domain.Account;
import com.master.authservice.domain.Institution;
import com.master.authservice.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
public class UserAccountEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String surname;

    private String email;

    private String password;

    @OneToOne(mappedBy = "user")
    private InstitutionEntity institutionEntity;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleEntity> roles;

    public UserAccountEntity(String name, String surname, String email, String password, List<RoleEntity> roles) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Account toDomain() {
        Account account = new Account();
        account.setId(this.id);
        account.setName(this.name);
        account.setSurname(this.surname);
        account.setEmail(this.email);

        if (this.institutionEntity != null) {
            Institution institution = new Institution();
            institution.setId(this.institutionEntity.getId());
            institution.setIdentificationNumber(this.institutionEntity.getIdentificationNumber());
            institution.setName(this.institutionEntity.getName());
            institution.setEntity(this.institutionEntity.getEntity());
            institution.setCanton(this.institutionEntity.getCanton());
            institution.setMunicipality(this.institutionEntity.getMunicipality());
            institution.setAddress(this.institutionEntity.getAddress());
            institution.setPhoneNumber(this.institutionEntity.getPhoneNumber());
            institution.setApproved(this.institutionEntity.isApproved());

            account.setInstitution(institution);
        }

        List<Role> roles = this.roles.stream()
                .map(roleEntity -> roleEntity.toDomain())
                .collect(Collectors.toList());

        account.setRoles(roles);

        return account;
    }
}
