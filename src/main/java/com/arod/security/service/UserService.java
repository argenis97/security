package com.arod.security.service;

import com.arod.security.dto.request.UserDTO;
import com.arod.security.dto.response.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserResponseDTO> findById(Long id);

    List<UserResponseDTO> findByPermission(Long permissionID);

    UserResponseDTO save(UserDTO request);

    boolean delete(Long id);

    Optional<UserResponseDTO> update(Long id, UserDTO request);

    Optional<UserResponseDTO> findByName(String name);

    List<UserResponseDTO> findAll();
}
