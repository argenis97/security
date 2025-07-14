package com.arod.security.dto.request;

import com.arod.security.dto.OrderLineDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private Long vendorID;
    private String description;
    private LocalDateTime date;
    private List<OrderLineDTO> orderLines;
}
