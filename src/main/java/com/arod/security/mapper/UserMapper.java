package com.arod.security.mapper;

import com.arod.security.dto.request.UserDTO;
import com.arod.security.dto.response.UserResponseDTO;
import com.arod.security.persistence.entity.AppUser;
import com.arod.security.persistence.entity.Role;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "userName", target = "name")
    @Mapping(target = "password", ignore = true)
    AppUser toEntity(UserDTO dto);

    @Mapping(source = "name", target = "userName")
    @Mapping(source = "user.role.id", target = "roleID")
    UserResponseDTO toDTO(AppUser user);

    @Mapping(source = "userName", target = "name")
    @Mapping(target = "password", ignore = true)
    void updateValues(UserDTO dto, @MappingTarget AppUser user);

    @AfterMapping
    default void afterMapping(UserDTO dto, @MappingTarget AppUser user) {
        Role role = new Role();
        role.setId(dto.getRoleID());
        user.setRole(role);
    }
}
