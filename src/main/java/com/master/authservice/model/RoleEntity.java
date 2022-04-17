package com.master.authservice.model;

import com.master.authservice.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue
    private UUID id;


    @NotEmpty(message = "Name cannot be empty.")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<UserAccountEntity> accounts;

    public RoleEntity(String name) {
        this.name = name;
    }

    public Role toDomain() {
        return new Role(this.getId(), this.getName());
    }
}
