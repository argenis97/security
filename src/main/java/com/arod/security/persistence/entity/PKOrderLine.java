package com.arod.security.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PKOrderLine {

    @ManyToOne
    @JoinColumn(name = "productID", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "orderID")
    private Order order;
}
