package com.arod.security.service.impl;

import com.arod.security.dto.request.ProductRequestDTO;
import com.arod.security.dto.response.ProductResponseDTO;
import com.arod.security.mapper.ProductMapper;
import com.arod.security.persistence.entity.Product;
import com.arod.security.persistence.repository.ProductRepository;
import com.arod.security.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<ProductResponseDTO> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse);
    }

    @Override
    public Optional<ProductResponseDTO> findByValue(String value) {
        return repository.findByValue(value)
                .map(mapper::toResponse);
    }

    @Override
    public Optional<ProductResponseDTO> update(Long id, ProductRequestDTO request) {

        Optional<Product> oProduct = repository.findById(id);

        if (oProduct.isEmpty())
            return Optional.empty();

        Product product = oProduct.get();
        mapper.updateValues(request, product);

        return Optional.of(repository.save(product))
                .map(mapper::toResponse);
    }

    @Override
    public ProductResponseDTO save(ProductRequestDTO request) {
        Product product = mapper.toEntity(request);
        return mapper.toResponse(repository.save(product));
    }

    @Override
    public boolean delete(Long id) {
        return repository.findById(id)
                .map(product -> {
                    repository.delete(product);
                    return true;
                }).isPresent();
    }

    @Override
    public List<ProductResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    private final ProductRepository repository;
    private final ProductMapper mapper;
}
