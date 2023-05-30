package com.example.car_sharing_sertvice_project.config;

import com.example.car_sharing_sertvice_project.model.Role;
import com.example.car_sharing_sertvice_project.model.Role.RoleName;
import com.example.car_sharing_sertvice_project.model.User;
import com.example.car_sharing_sertvice_project.service.RoleService;
import com.example.car_sharing_sertvice_project.service.UserService;
import jakarta.annotation.PostConstruct;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        Role customerRole = new Role();
        customerRole.setRoleName(RoleName.CUSTOMER);
        roleService.add(customerRole);
        Role managerRole = new Role();
        managerRole.setRoleName(RoleName.MANAGER);
        roleService.add(managerRole);

        User user = new User();
        user.setEmail("bob@io.com");
        user.setPassword(passwordEncoder.encode("123"));
        user.setFirstName("bob");
        user.setLastName("bobov");
        user.setRoles(Set.of(customerRole));
        userService.add(user);

        User manager = new User();
        manager.setEmail("man@io.com");
        manager.setPassword(passwordEncoder.encode("12345"));
        manager.setFirstName("man");
        manager.setLastName("manov");
        manager.setRoles(Set.of(managerRole));
        userService.add(manager);
    }
}
