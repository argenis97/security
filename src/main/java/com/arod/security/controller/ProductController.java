package com.arod.security.controller;

import com.arod.security.dto.request.ProductRequestDTO;
import com.arod.security.dto.response.ProductResponseDTO;
import com.arod.security.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('WRITE_PRODUCT')")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductRequestDTO product) {
        return ResponseEntity.ok(service.save(product));
    }

    @PreAuthorize("hasAuthority('WRITE_PRODUCT')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable("id") Long id
            , @RequestBody ProductRequestDTO product) {
        return service.update(id, product)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        return Optional.of(service.delete(id))
                .filter(del -> del)
                .map(del -> ResponseEntity.ok().build())
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PreAuthorize("hasAuthority('READ_PRODUCT')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> get(@PathVariable("id") Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PreAuthorize("hasAuthority('READ_PRODUCT')")
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> get() {
        return Optional.of(service.findAll())
                .filter(list -> !list.isEmpty())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    private final ProductService service;
}
