package com.example.car_sharing_sertvice_project.service.impl;

import com.example.car_sharing_sertvice_project.model.Role;
import com.example.car_sharing_sertvice_project.model.Role.RoleName;
import com.example.car_sharing_sertvice_project.repository.RoleRepository;
import com.example.car_sharing_sertvice_project.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role add(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(RoleName.valueOf(roleName.toUpperCase())).orElseThrow(
                () -> new EntityNotFoundException("Role not found with roleName: " + roleName));
    }
}
