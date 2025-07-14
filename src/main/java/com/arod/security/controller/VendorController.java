package com.arod.security.controller;

import com.arod.security.dto.request.VendorRequestDTO;
import com.arod.security.dto.response.VendorResponseDTO;
import com.arod.security.service.VendorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    public VendorController(VendorService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('WRITE_VENDOR')")
    @PostMapping
    public ResponseEntity<VendorResponseDTO> create(@RequestBody VendorRequestDTO vendor) {
        return ResponseEntity.ok(service.save(vendor));
    }

    @PreAuthorize("hasAuthority('WRITE_VENDOR')")
    @PutMapping("/{id}")
    public ResponseEntity<VendorResponseDTO> update(@RequestBody VendorRequestDTO vendor
            , @PathVariable("id") Long id) {
        return service.update(id, vendor)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PreAuthorize("hasAuthority('DELETE_VENDOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
        return Optional.of(service.delete(id))
                .filter(del -> del)
                .map(del -> ResponseEntity.ok().build())
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PreAuthorize("hasAuthority('READ_VENDOR')")
    @GetMapping("/{id}")
    public ResponseEntity<VendorResponseDTO> get(@PathVariable("id") Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PreAuthorize("hasAuthority('READ_VENDOR')")
    @GetMapping
    public ResponseEntity<List<VendorResponseDTO>> getAll() {
        return Optional.of(service.findAll())
                .filter(vendors -> !vendors.isEmpty())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    private final VendorService service;
}
