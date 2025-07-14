package com.arod.security.service.impl;

import com.arod.security.dto.request.VendorRequestDTO;
import com.arod.security.dto.response.VendorResponseDTO;
import com.arod.security.mapper.VendorMapper;
import com.arod.security.persistence.entity.Vendor;
import com.arod.security.persistence.repository.VendorRepository;
import com.arod.security.service.VendorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorServiceImpl implements VendorService {

    public VendorServiceImpl(VendorRepository repository, VendorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<VendorResponseDTO> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse);
    }

    @Override
    public Optional<VendorResponseDTO> findByTaxID(String taxID) {
        return repository.findByTaxID(taxID)
                .map(mapper::toResponse);
    }

    @Override
    public VendorResponseDTO save(VendorRequestDTO vendorRequest) {
        Vendor vendor = mapper.toEntity(vendorRequest);
        return mapper.toResponse(repository.save(vendor));
    }

    @Override
    public Optional<VendorResponseDTO> update(Long vendorID, VendorRequestDTO request) {

        Optional<Vendor> oVendor = repository.findById(vendorID);

        if (oVendor.isEmpty())
            return Optional.empty();

        Vendor vendor = oVendor.get();
        mapper.update(request, vendor);

        return Optional.of(repository.save(vendor))
                .map(mapper::toResponse);
    }

    @Override
    public boolean delete(Long id) {
        return repository.findById(id)
                .map(vendor -> {
                    repository.delete(vendor);
                    return true;
                }).isPresent();
    }

    @Override
    public List<VendorResponseDTO> findAll() {
        return mapper.toListResponse(repository.findAll());
    }

    private final VendorRepository repository;
    private final VendorMapper mapper;
}
