package com.example.carsharing.config;

import com.example.carsharing.model.UserRole;
import com.example.carsharing.model.UserRole.RoleName;
import com.example.carsharing.model.User;
import com.example.carsharing.service.RoleService;
import com.example.carsharing.service.UserService;
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
        UserRole customerRole = new UserRole();
        customerRole.setRoleName(RoleName.CUSTOMER);
        roleService.add(customerRole);
        UserRole managerRole = new UserRole();
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
