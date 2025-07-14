package com.arod.security.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vendor", schema = "application"
        , indexes = {@Index(name = "taxid_uq", columnList = "taxID", unique = true)})
public class Vendor {

    @Id
    @GeneratedValue(generator = "vendor_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "vendor_gen", sequenceName = "vendor_seq", schema = "application")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String name2;

    @Column(nullable = false)
    private String lastName;

    @Column
    private String lastName2;

    @Column
    private String taxID;
}
