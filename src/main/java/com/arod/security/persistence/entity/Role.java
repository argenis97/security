package com.arod.security.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "role", schema = "application")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(generator = "role_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "role_gen", schema = "application", sequenceName = "role_seq")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<AppUser> users;

    @OneToMany(mappedBy = "role", cascade = {CascadeType.MERGE, CascadeType.PERSIST
            , CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
    private List<RolePermission> permissions;
}
