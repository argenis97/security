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
@Table(name = "order-line", schema = "application")
public class OrderLine {

    @EmbeddedId
    private PKOrderLine id;

    @Column(nullable = false)
    private BigDecimal qty;

    @Column(nullable = false)
    private BigDecimal price;
}
