package com.arod.security.service.impl;

import com.arod.security.dto.UserDetailsCustom;
import com.arod.security.dto.request.AuthRequestDTO;
import com.arod.security.dto.request.RefreshRequestDTO;
import com.arod.security.dto.response.AuthResponseDTO;
import com.arod.security.exception.InvalidTokenException;
import com.arod.security.exception.RefreshTokenNotFound;
import com.arod.security.exception.TokenExpiredException;
import com.arod.security.persistence.entity.AppUser;
import com.arod.security.persistence.entity.RefreshToken;
import com.arod.security.persistence.repository.RefreshTokenRepository;
import com.arod.security.service.AuthenticationService;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    public AuthenticationServiceImpl(JWTUtil jwtUtil, AuthenticationManager authManager
        , RefreshTokenRepository tokenRepository, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.tokenRepository = tokenRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    @Transactional
    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName()
                , request.getPassword()));

        UserDetailsCustom userDetails = (UserDetailsCustom) auth.getPrincipal();

        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        generateRefreshToken(userDetails, refreshToken);

        return new AuthResponseDTO(userDetails.getUsername()
                , jwtUtil.generateToken(userDetails)
                , refreshToken);
    }

    protected void generateRefreshToken(UserDetailsCustom user, String token) {
        Claims claims = jwtUtil.getClaims(token);

        RefreshToken rToken = new RefreshToken();
        rToken.setToken(token);
        rToken.setAttempts(0L);
        rToken.setRevoked(false);
        rToken.setGenDate(LocalDateTime.now());
        rToken.setExpirationDate(new Timestamp(claims.getExpiration().getTime()).toLocalDateTime());

        AppUser appUser = new AppUser();
        appUser.setId(user.getId());

        rToken.setUser(appUser);

        tokenRepository.save(rToken);
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

        Optional<RefreshToken> oRefresh = tokenRepository.findByToken(token);

        if (oRefresh.isEmpty())
            throw new RefreshTokenNotFound("token.not.found");

        UserDetailsCustom user = (UserDetailsCustom) userDetailsService.loadUserByUsername(userName);

        if (!jwtUtil.isValid(claims, user))
            throw new InvalidTokenException("token.invalid");

        RefreshToken refer = oRefresh.get();

        if (refer.isRevoked())
        {
            /*refer.setAttempts(refer.getAttempts() + 1);
            tokenRepository.save(refer);*/
            throw new InvalidTokenException("refresh.token.revoked");
        }

        refer.setRevoked(true);
        tokenRepository.save(refer);

        String refreshToken = jwtUtil.generateRefreshToken(user);

        generateRefreshToken(user, refreshToken);

        return new AuthResponseDTO(userName
                , jwtUtil.generateToken(user)
                , refreshToken);
    }

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final RefreshTokenRepository tokenRepository;
    private final UserDetailsService userDetailsService;
}
