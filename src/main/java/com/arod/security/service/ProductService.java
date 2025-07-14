package com.arod.security.service;

import com.arod.security.dto.request.ProductRequestDTO;
import com.arod.security.dto.response.ProductResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<ProductResponseDTO> findById(Long id);

    Optional<ProductResponseDTO> findByValue(String value);

    Optional<ProductResponseDTO> update(Long id, ProductRequestDTO request);

    ProductResponseDTO save(ProductRequestDTO request);

    boolean delete(Long id);

    List<ProductResponseDTO> findAll();
}
