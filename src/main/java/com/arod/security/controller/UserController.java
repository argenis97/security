package com.arod.security.controller;

import com.arod.security.dto.request.UserDTO;
import com.arod.security.dto.response.UserResponseDTO;
import com.arod.security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserDTO user) {
        return new ResponseEntity<>(service.save(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable("id") Long id, @RequestBody UserDTO user) {
        return service.update(id, user)
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
    public ResponseEntity<UserResponseDTO> get(@PathVariable("id") Long id) {
        return service.findById(id)
                .map(ResponseEntity.ok()::body)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> get() {
        return Optional.of(service.findAll())
                .filter(users -> !users.isEmpty())
                .map(ResponseEntity.ok()::body)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    private final UserService service;
}
