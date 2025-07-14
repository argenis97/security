package com.arod.security.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "application", name = "product"
        , indexes = {@Index(name = "product_uq", columnList = "name", unique = true)
                , @Index(name = "value_uq", columnList = "value", unique = true)})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_gen")
    @SequenceGenerator(name = "product_gen", schema = "application", sequenceName = "product_seq")
    private Long id;

    @Column(nullable = false)
    private String value;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;
}
