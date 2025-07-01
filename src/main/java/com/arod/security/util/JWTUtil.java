package com.arod.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Component
public class JWTUtil {

    public JWTUtil(@Value("${token.secret}") String key
            , @Value("${token.expiration}") Long expiration) {
        this.expiration = expiration;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);

        if (!StringUtils.hasText(token))
            return null;

        if (token.contains(BEARER))
            token = token.replaceAll(BEARER, "");

        return token;
    }

    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .signWith(key)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000 * expiration))
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserName(Claims claims) {
        return Optional.ofNullable(claims)
                .map(Claims::getSubject)
                .orElse("");
    }

    public Date getExpiration(Claims claims) {
        return Optional.ofNullable(claims)
                .map(Claims::getExpiration)
                .orElse(null);
    }

    public boolean isValid(Claims claims, UserDetails user) {
        return Optional.ofNullable(claims)
                .filter(clm -> clm.getExpiration().compareTo(new Date(System.currentTimeMillis())) > 0
                    && clm.getSubject().equals(user.getUsername()))
                .isPresent();
    }

    private final Long expiration;
    private final Key key;

    private static final String AUTHORIZATION = "authorization";
    private static final String BEARER = "Bearer";
}
