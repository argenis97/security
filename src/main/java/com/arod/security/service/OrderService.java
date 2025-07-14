package com.arod.security.service;

import com.arod.security.dto.request.OrderRequestDTO;
import com.arod.security.dto.response.OrderResponseDTO;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<OrderResponseDTO> findById(Long id);

    List<OrderResponseDTO> findByVendorID(Long vendorID);

    List<OrderResponseDTO> findAll();

    OrderResponseDTO save(OrderRequestDTO request);

    Optional<OrderResponseDTO> update(Long id, OrderRequestDTO request);

    boolean delete(Long id);
}
