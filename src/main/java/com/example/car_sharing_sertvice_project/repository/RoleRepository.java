package com.example.car_sharing_sertvice_project.repository;

import com.example.car_sharing_sertvice_project.model.Role;
import com.example.car_sharing_sertvice_project.model.Role.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);
}
