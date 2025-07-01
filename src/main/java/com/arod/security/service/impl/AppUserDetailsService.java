package com.arod.security.service.impl;

import com.arod.security.mapper.UserDetailMapper;
import com.arod.security.persistence.entity.AppUser;
import com.arod.security.persistence.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    public AppUserDetailsService(UserRepository repository, UserDetailMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByName(username)
                .map(mapper::toUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario " + username + " no encontrado"));
    }

    private final UserRepository repository;
    private final UserDetailMapper mapper;
}
