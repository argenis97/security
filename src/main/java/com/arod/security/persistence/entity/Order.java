package com.arod.security.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order", schema = "application")
public class Order {

    @Id
    @GeneratedValue(generator = "order_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "order_gen", schema = "application", sequenceName = "order_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vendorID", nullable = false)
    private Vendor vendor;

    @Column
    private String comment;

    @Column(nullable = false)
    private LocalDateTime dateOrder;

    @OneToMany(mappedBy = "id.order", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<OrderLine> lines;
}
