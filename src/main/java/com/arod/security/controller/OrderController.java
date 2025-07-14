package com.arod.security.controller;

import com.arod.security.dto.request.OrderRequestDTO;
import com.arod.security.dto.response.OrderResponseDTO;
import com.arod.security.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('WRITE_ORDER')")
    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(OrderRequestDTO order) {
        return ResponseEntity.ok(service.save(order));
    }

    @PreAuthorize("hasAuthority('WRITE_ORDER')")
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> update(@PathVariable("id") Long id
            , @RequestBody OrderRequestDTO order) {
        return service.update(id, order)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PreAuthorize("hasAuthority('DELETE_ORDER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        return Optional.of(service.delete(id))
                .filter(del -> del)
                .map(del -> ResponseEntity.ok().build())
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PreAuthorize("hasAuthority('READ_ORDER')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> get(@PathVariable("id") Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PreAuthorize("hasAuthority('READ_ORDER')")
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> get() {
        return Optional.of(service.findAll())
                .filter(orders -> !orders.isEmpty())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PreAuthorize("hasAuthority('READ_ORDER')")
    @GetMapping("/vendor/{vendorID}")
    public ResponseEntity<List<OrderResponseDTO>> getByVendorID(@PathVariable("vendorID") Long vendorID) {
        return Optional.of(service.findByVendorID(vendorID))
                .filter(orders -> !orders.isEmpty())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    private final OrderService service;
}
