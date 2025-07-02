package com.arod.security.service;

import com.arod.security.dto.request.RoleDTO;
import com.arod.security.dto.response.RoleResponse;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Optional<RoleResponse> findById(Long id);

    List<RoleResponse> findByPermission(Long permissionID);

    RoleResponse save(RoleDTO role);

    boolean delete(Long id);

    Optional<RoleResponse> update(Long id, RoleDTO role);

    List<RoleResponse> findAll();
}
