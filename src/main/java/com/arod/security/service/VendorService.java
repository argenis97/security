package com.arod.security.service;

import com.arod.security.dto.request.VendorRequestDTO;
import com.arod.security.dto.response.VendorResponseDTO;

import java.util.List;
import java.util.Optional;

public interface VendorService {
    Optional<VendorResponseDTO> findById(Long id);

    Optional<VendorResponseDTO> findByTaxID(String taxID);

    VendorResponseDTO save(VendorRequestDTO vendorRequest);

    Optional<VendorResponseDTO> update(Long vendorID, VendorRequestDTO request);

    boolean delete(Long id);

    List<VendorResponseDTO> findAll();
}
