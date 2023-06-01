package com.example.carsharing.service.impl;

import com.example.carsharing.model.UserRole;
import com.example.carsharing.model.UserRole.RoleName;
import com.example.carsharing.repository.RoleRepository;
import com.example.carsharing.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public UserRole add(UserRole role) {
        return roleRepository.save(role);
    }

    @Override
    public UserRole findByRoleName(String roleName) {
        return roleRepository.findByRoleName(RoleName.valueOf(roleName.toUpperCase())).orElseThrow(
                () -> new EntityNotFoundException("Role not found with roleName: " + roleName));
    }
}
