package com.arod.security.service.impl;

import com.arod.security.dto.UserDetailsCustom;
import com.arod.security.dto.request.AuthRequestDTO;
import com.arod.security.dto.request.RefreshRequestDTO;
import com.arod.security.dto.response.AuthResponseDTO;
import com.arod.security.exception.InvalidTokenException;
import com.arod.security.exception.TokenExpiredException;
import com.arod.security.persistence.entity.RefreshToken;
import com.arod.security.service.AuthenticationService;
import com.arod.security.service.RefreshTokenService;
import com.arod.security.util.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    public AuthenticationServiceImpl(JWTUtil jwtUtil, AuthenticationManager authManager
        , UserDetailsService userDetailsService, RefreshTokenService tokenService) {
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional
    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName()
                , request.getPassword()));

        UserDetailsCustom userDetails = (UserDetailsCustom) auth.getPrincipal();

        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        tokenService.createToken(userDetails.getId(), refreshToken);

        return new AuthResponseDTO(userDetails.getUsername()
                , jwtUtil.generateToken(userDetails)
                , refreshToken);
    }

    @Override
    @Transactional
    public AuthResponseDTO refresh(RefreshRequestDTO refresh) {

        String token = refresh.getRefreshToken();

        Claims claims = jwtUtil.getClaims(token);

        if (jwtUtil.isExpired(claims))
            throw new TokenExpiredException("token.expired");

        String userName = jwtUtil.getUserName(claims);

        if (!StringUtils.hasText(userName))
            throw new InvalidTokenException("absent.token.username");

        RefreshToken refer = tokenService.findByToken(token);

        UserDetailsCustom user = (UserDetailsCustom) userDetailsService.loadUserByUsername(userName);

        if (!jwtUtil.isValid(claims, user))
            throw new InvalidTokenException("token.invalid");

        if (refer.isRevoked())
        {
            tokenService.addAttempt(refer);
            throw new InvalidTokenException("refresh.token.revoked");
        }

        tokenService.revoke(refer);

        String refreshToken = jwtUtil.generateRefreshToken(user);

        tokenService.createToken(user.getId(), refreshToken);

        return new AuthResponseDTO(userName
                , jwtUtil.generateToken(user)
                , refreshToken);
    }

    @Override
    public boolean logout(RefreshRequestDTO refresh) {

        String token = refresh.getRefreshToken();

        Claims claims = jwtUtil.getClaims(token);

        if (jwtUtil.isExpired(claims))
            throw new TokenExpiredException("token.expired");

        String userName = jwtUtil.getUserName(claims);

        if (!StringUtils.hasText(userName))
            throw new InvalidTokenException("absent.token.username");

        UserDetails user = userDetailsService.loadUserByUsername(userName);

        if (!jwtUtil.isValid(claims, user))
            throw new InvalidTokenException("token.invalid");

        RefreshToken refreshToken = tokenService.findByToken(token);

        tokenService.revoke(refreshToken);

        return true;
    }

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenService tokenService;
}
