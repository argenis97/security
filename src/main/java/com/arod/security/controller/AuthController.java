package com.arod.security.controller;

import com.arod.security.dto.request.AuthRequestDTO;
import com.arod.security.dto.response.AuthResponseDTO;
import com.arod.security.util.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public AuthController(AuthenticationManager authManager, JWTUtil jwtUtil){
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO userData) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(userData.getUserName()
                , userData.getPassword()));

        UserDetails user = (UserDetails) auth.getPrincipal();

        return ResponseEntity.ok(new AuthResponseDTO(userData.getUserName(), jwtUtil.generateToken(user)));
    }

    private final AuthenticationManager authManager;
    private final JWTUtil jwtUtil;
}
