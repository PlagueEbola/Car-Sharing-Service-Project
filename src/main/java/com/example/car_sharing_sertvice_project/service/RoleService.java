package com.example.car_sharing_sertvice_project.service;

import com.example.car_sharing_sertvice_project.model.Role;

public interface RoleService {
    Role add(Role role);

    Role findByRoleName(String roleName);
}
