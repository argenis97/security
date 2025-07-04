package com.arod.security.mapper;

import com.arod.security.dto.UserDetailsCustom;
import com.arod.security.persistence.entity.AppUser;
import com.arod.security.persistence.entity.RolePermission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDetailMapper {

    default List<SimpleGrantedAuthority> toAuthorities(List<RolePermission> permissions) {
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .toList();
    }

    default List<String> toAuthorityNames(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    @Mapping(source = "name", target = "username")
    @Mapping(source = "user.role.permissions", target = "authorities")
    UserDetailsCustom toUserDetails(AppUser user);
}
