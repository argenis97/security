package com.arod.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsCustom implements UserDetails {
    private String username;
    private String password;
    private Long id;
    private List<SimpleGrantedAuthority> authorities;
}
