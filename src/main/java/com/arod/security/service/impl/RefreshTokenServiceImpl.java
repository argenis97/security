package com.arod.security.service.impl;

import com.arod.security.exception.RefreshTokenNotFound;
import com.arod.security.persistence.entity.AppUser;
import com.arod.security.persistence.entity.RefreshToken;
import com.arod.security.persistence.repository.RefreshTokenRepository;
import com.arod.security.service.RefreshTokenService;
import com.arod.security.util.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    public RefreshTokenServiceImpl(RefreshTokenRepository repository, JWTUtil jwtUtil) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional
    public RefreshToken createToken(Long userID, String refreshToken) {
        Claims claims = jwtUtil.getClaims(refreshToken);

        RefreshToken rToken = new RefreshToken();
        rToken.setToken(refreshToken);
        rToken.setAttempts(0L);
        rToken.setRevoked(false);
        rToken.setGenDate(LocalDateTime.now());
        rToken.setExpirationDate(new Timestamp(claims.getExpiration().getTime()).toLocalDateTime());

        AppUser appUser = new AppUser();
        appUser.setId(userID);

        rToken.setUser(appUser);

        return repository.save(rToken);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addAttempt(RefreshToken refreshToken) {
        refreshToken.setAttempts(refreshToken.getAttempts() + 1);
        repository.save(refreshToken);
    }

    @Override
    @Transactional
    public void revoke(RefreshToken refreshToken) {
        refreshToken.setRevoked(true);
        repository.save(refreshToken);
    }

    @Override
    public RefreshToken findByToken(String refreshToken) {
        return repository.findByToken(refreshToken)
                .orElseThrow(() -> new RefreshTokenNotFound("token.not.found"));
    }

    private final RefreshTokenRepository repository;
    private final JWTUtil jwtUtil;
}
