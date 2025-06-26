package com.arod.security.mapper;

import com.arod.security.dto.request.RoleDTO;
import com.arod.security.dto.response.RoleResponse;
import com.arod.security.persistence.entity.Role;
import com.arod.security.persistence.entity.RolePermission;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(source = "name", target = "roleName")
    RoleResponse toDTO(Role role);

    @Mapping(source = "roleName", target = "name")
    Role toEntity(RoleDTO dto);

    @Mapping(source = "roleName", target = "name")
    void update(RoleDTO dto, @MappingTarget Role role);

    default void updatePermissions(List<String> permissionNames
            , @MappingTarget List<RolePermission> permissions){
        permissionNames.forEach(permissionName -> addPermName(permissionName, permissions));
    }

    default void addPermName(String permissionName, List<RolePermission> permissions){
        if (permissions.stream().anyMatch(permission -> permission.getName().equals(permissionName)))
            return ;

        permissions.add(createPermissionByName(permissionName));
    }

    @AfterMapping
    default void afterMappingRole(@MappingTarget Role role) {
        role.getPermissions().forEach(perm -> perm.setRole(role));
    }

    default List<String> toPermissionName(List<RolePermission> permissions) {
        return permissions
                .stream()
                .map(RolePermission::getName)
                .toList();
    }

    default List<RolePermission> toRolePermission(List<String> permissionName) {
        return permissionName
                .stream()
                .map(this::createPermissionByName)
                .toList();
    }

    default RolePermission createPermissionByName(String permissionName){
        RolePermission permission = new RolePermission();
        permission.setName(permissionName);

        return permission;
    }
}
