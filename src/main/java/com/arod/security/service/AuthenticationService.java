package com.arod.security.service;

import com.arod.security.dto.request.AuthRequestDTO;
import com.arod.security.dto.request.RefreshRequestDTO;
import com.arod.security.dto.response.AuthResponseDTO;

public interface AuthenticationService {
    AuthResponseDTO authenticate(AuthRequestDTO request);

    AuthResponseDTO refresh(RefreshRequestDTO refresh);

    boolean logout(RefreshRequestDTO refresh);
}
