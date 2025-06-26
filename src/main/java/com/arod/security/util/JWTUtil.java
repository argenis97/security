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

    public JWTUtil(@Value("${token.expiration}") Long expiration
            , @Value("${token.secret}") String secret) {
        this.expiration = expiration;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generateToken(UserDetails user){
        return Jwts.builder()
                .signWith(key)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * expiration))
                .compact();
    }

    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);

        if (StringUtils.hasText(token) && token.contains(BEARER))
            token = token.replaceAll(BEARER, "");

        return token;
    }

    public String getSubject(Claims claims) {
        return Optional.ofNullable(claims)
                .map(Claims::getSubject)
                .orElse(null);
    }

    public Date getExpiration(Claims claims) {
        return Optional.ofNullable(claims)
                .map(Claims::getExpiration)
                .orElse(null);
    }

    public boolean isExpired(String token) {
        return isExpired(getClaims(token));
    }

    public boolean isExpired(Claims claims) {
        return Optional.ofNullable(claims)
                .filter(clm -> clm.getExpiration().compareTo(new Date(System.currentTimeMillis())) > 0)
                .isEmpty();
    }

    public Claims getClaims(String token){
        return Optional.ofNullable(token)
                .map(tok -> Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(tok)
                        .getBody())
                .orElse(null);
    }

    public boolean validateToken(UserDetails user, String token) {
        return validateToken(user, getClaims(token));
    }

    public boolean validateToken(UserDetails user, Claims claims) {
        return Optional.ofNullable(claims)
                .filter(clm -> !isExpired(clm) && clm.getSubject().equals(user.getUsername()))
                .isPresent();
    }

    private static final String AUTHORIZATION = "authorization";
    private static final String BEARER = "Bearer";

    private final Long expiration;
    private final Key key;
}
