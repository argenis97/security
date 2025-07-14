package com.arod.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineDTO {
    private Long productID;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
}
