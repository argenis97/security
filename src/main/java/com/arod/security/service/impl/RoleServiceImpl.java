package com.arod.security.service.impl;

import com.arod.security.dto.request.RoleDTO;
import com.arod.security.dto.response.RoleResponse;
import com.arod.security.mapper.RoleMapper;
import com.arod.security.persistence.entity.Role;
import com.arod.security.persistence.entity.RolePermission;
import com.arod.security.persistence.repository.PermissionRepository;
import com.arod.security.persistence.repository.RoleRepository;
import com.arod.security.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    public RoleServiceImpl(RoleRepository repository, PermissionRepository permissionRepository
            , RoleMapper mapper) {
        this.repository = repository;
        this.permissionRepository = permissionRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<RoleResponse> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public List<RoleResponse> findByPermission(Long permissionID) {
        return repository.findByPermissionID(permissionID)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public RoleResponse save(RoleDTO role) {
        return mapper.toDTO(repository.save(mapper.toEntity(role)));
    }

    @Override
    public boolean delete(Long id) {
        return repository.findById(id)
                .map(role -> {
                    repository.delete(role);
                    return true;
                }).orElse(false);
    }

    @Override
    public Optional<RoleResponse> update(Long id, RoleDTO role) {
        Optional<Role> oRole = repository.findById(id);

        if (oRole.isEmpty())
            return Optional.empty();

        Role entity = oRole.get();

        List<Long> permissionDelete = entity.getPermissions()
                        .stream().filter(perm -> !existsPermName(perm, role.getPermissions()))
                .map(RolePermission::getId).toList();

        permissionRepository.deleteAllById(permissionDelete);

        entity.getPermissions().removeIf(perm -> permissionDelete.contains(perm.getId()));

        mapper.update(role, entity);

        return Optional.of(mapper.toDTO(repository.save(entity)));
    }

    protected boolean existsPermName(RolePermission permission, List<String> permissionNames){
        return permissionNames.stream().anyMatch(permName -> permName.equals(permission.getName()));
    }

    private final RoleRepository repository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper mapper;
}
