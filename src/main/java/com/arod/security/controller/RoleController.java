package com.arod.security.controller;

import com.arod.security.dto.request.RoleDTO;
import com.arod.security.dto.response.RoleResponse;
import com.arod.security.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/role")
public class RoleController {

    public RoleController(RoleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RoleResponse> create(@RequestBody RoleDTO role) {
        return new ResponseEntity<>(service.save(role), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> update(@PathVariable("id") Long id, @RequestBody RoleDTO role) {
        return service.update(id, role)
                .map(ResponseEntity.ok()::body)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        return Optional.of(service.delete(id))
                .filter(del -> del)
                .map(del -> ResponseEntity.ok().build())
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> get(@PathVariable("id") Long id) {
        return service.findById(id)
                .map(ResponseEntity.ok()::body)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> get() {
        return Optional.of(service.findAll())
                .filter(roles -> !roles.isEmpty())
                .map(ResponseEntity.ok()::body)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    private final RoleService service;
}
