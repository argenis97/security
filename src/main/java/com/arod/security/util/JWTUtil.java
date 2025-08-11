package com.arod.security.util;

import com.arod.security.config.TokenProperties;
import com.arod.security.mapper.UserDetailMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Component
public class JWTUtil {

    public JWTUtil(UserDetailMapper mapper, TokenProperties tokenProperties) {
        this.expiration = tokenProperties.getExpiration();
        this.expirationRefresh = tokenProperties.getRefresh();
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenProperties.getSecret()));
        this.mapper = mapper;
    }

    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);

        if (!StringUtils.hasText(token))
            return null;

        if (token.contains(BEARER))
            token = token.replaceAll(BEARER, "");

        return token;
    }

    /**
     *
     * @author Argenis Rodríguez
     * @param user
     * @return access token
     */
    public String generateToken(UserDetails user) {
        return generateToken(user, expiration * 60 * 1000);
    }

    /**
     *
     * @author Argenis Rodríguez
     * @param user
     * @return refresh token
     */
    public String generateRefreshToken(UserDetails user) {
        return generateToken(user, expirationRefresh * 60 * 60 * 1000);
    }

    protected String generateToken(UserDetails user, Long expiration) {
        return Jwts.builder()
                .signWith(key)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .claim("permissions", mapper.toAuthorityNames(user.getAuthorities()))
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

    public boolean isExpired(Claims claims) {
        return Optional.ofNullable(claims)
                .filter(cls -> cls.getExpiration().compareTo(new Date(System.currentTimeMillis())) <= 0)
                .isPresent();
    }

    private final Long expiration;
    private final Long expirationRefresh;
    private final Key key;
    private final UserDetailMapper mapper;

    private static final String AUTHORIZATION = "authorization";
    private static final String BEARER = "Bearer";
}
