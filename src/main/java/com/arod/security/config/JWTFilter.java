package com.arod.security.config;

import com.arod.security.exception.AbsentTokenException;
import com.arod.security.exception.InvalidTokenException;
import com.arod.security.exception.TokenExpiredException;
import com.arod.security.util.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    public JWTFilter(JWTUtil jwtUtil, UserDetailsService service) {
        this.jwtUtil = jwtUtil;
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UsernamePasswordAuthenticationToken authenticationToken = authenticate(request);

        if (authenticationToken != null)
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    protected UsernamePasswordAuthenticationToken authenticate(HttpServletRequest request) {
        String token = jwtUtil.getToken(request);

        if (!StringUtils.hasText(token))
            throw new AbsentTokenException("absent.token");

        Claims claims = jwtUtil.getClaims(token);

        String userName = jwtUtil.getUserName(claims);

        if (!StringUtils.hasText(userName))
            throw new InvalidTokenException("absent.token.username");

        UserDetails user = service.loadUserByUsername(userName);

        if (jwtUtil.isExpired(claims))
            throw new TokenExpiredException("token.expired");

        if (!jwtUtil.isValid(claims, user))
            throw new InvalidTokenException("token.invalid");

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getUsername()
                , null, user.getAuthorities());

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return auth;
    }

    private final JWTUtil jwtUtil;
    private final UserDetailsService service;
}
