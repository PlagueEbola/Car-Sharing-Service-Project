package com.example.carsharing.service;

import com.example.carsharing.model.UserRole;

public interface RoleService {
    UserRole add(UserRole role);

    UserRole findByRoleName(String roleName);
}
