package com.arod.security.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user", schema = "application")
public class AppUser {

    @Id
    @GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(schema = "application", name = "user_gen", sequenceName = "user_seq")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;
}
