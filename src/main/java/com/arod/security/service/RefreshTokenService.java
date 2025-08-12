package com.arod.security.service;

import com.arod.security.persistence.entity.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createToken(Long userID, String refreshToken);

    void addAttempt(RefreshToken refreshToken);

    void revoke(RefreshToken refreshToken);

    RefreshToken findByToken(String refreshToken);
}
