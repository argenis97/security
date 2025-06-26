package com.arod.security.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "permission", schema = "application"
        , indexes = {@Index(name = "role_name", unique = true, columnList = "name,role_id")})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission {

    @Id
    @GeneratedValue(generator = "permission_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "permission_gen", schema = "application", sequenceName = "permission_seq")
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
