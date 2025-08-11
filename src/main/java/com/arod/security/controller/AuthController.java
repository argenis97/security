package com.arod.security.controller;

import com.arod.security.dto.request.AuthRequestDTO;
import com.arod.security.dto.request.RefreshRequestDTO;
import com.arod.security.dto.response.AuthResponseDTO;
import com.arod.security.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public AuthController(AuthenticationService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO userData) {
        return ResponseEntity.ok(service.authenticate(userData));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@RequestBody RefreshRequestDTO refreshData) {
        return ResponseEntity.ok(service.refresh(refreshData));
    }

    private final AuthenticationService service;
}
