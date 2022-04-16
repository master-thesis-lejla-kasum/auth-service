package com.master.authservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "role")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<UserAccount> accounts;

    public Role(String name) {
        this.name = name;
    }
}
