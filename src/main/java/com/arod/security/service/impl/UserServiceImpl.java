package com.arod.security.service.impl;

import com.arod.security.dto.request.UserDTO;
import com.arod.security.dto.response.UserResponseDTO;
import com.arod.security.mapper.UserMapper;
import com.arod.security.persistence.entity.AppUser;
import com.arod.security.persistence.repository.UserRepository;
import com.arod.security.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    public UserServiceImpl(UserRepository repository, UserMapper mapper, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.encoder = encoder;
    }

    @Override
    public Optional<UserResponseDTO> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public List<UserResponseDTO> findByPermission(Long permissionID) {
        return repository.findByPermission(permissionID)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public UserResponseDTO save(UserDTO request) {
        AppUser user = mapper.toEntity(request);
        user.setPassword(encoder.encode(request.getPassword()));

        user = repository.save(user);

        return mapper.toDTO(user);
    }

    @Override
    public boolean delete(Long id) {
        return repository.findById(id)
                .map(appUser -> {
                    repository.delete(appUser);
                    return true;
                }).isPresent();
    }

    @Override
    public Optional<UserResponseDTO> update(Long id, UserDTO request) {

        Optional<AppUser> oUser = repository.findByIdLazy(id);

        if (oUser.isEmpty())
            return Optional.empty();

        AppUser user = oUser.get();

        mapper.updateValues(request, user);

        if (StringUtils.hasText(request.getPassword()))
            user.setPassword(encoder.encode(request.getPassword()));

        user = repository.save(user);

        return Optional.ofNullable(mapper.toDTO(user));
    }

    @Override
    public Optional<UserResponseDTO> findByName(String name) {
        return repository.findByName(name)
                .map(mapper::toDTO);
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    private final UserRepository repository;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder encoder;
}
