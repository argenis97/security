package com.arod.security.service.impl;

import com.arod.security.mapper.UserDetailMapper;
import com.arod.security.persistence.entity.AppUser;
import com.arod.security.persistence.repository.UserRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    public AppUserDetailsService(UserRepository repository, UserDetailMapper mapper
        , MessageSource messageSource) {
        this.repository = repository;
        this.mapper = mapper;
        this.messageSource = messageSource;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByName(username)
                .map(mapper::toUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(
                        messageSource.getMessage("user.not.found"
                            , new Object[] {username}
                            , LocaleContextHolder.getLocale())));
    }

    private final UserRepository repository;
    private final UserDetailMapper mapper;
    private final MessageSource messageSource;
}
