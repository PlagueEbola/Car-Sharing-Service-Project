package com.example.car_sharing_sertvice_project.security;

import com.example.car_sharing_sertvice_project.exception.AuthenticationException;
import com.example.car_sharing_sertvice_project.model.User;
import com.example.car_sharing_sertvice_project.service.RoleService;
import com.example.car_sharing_sertvice_project.service.UserService;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(String email, String password, String firstName, String lastName) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRoles(Set.of(roleService.findByRoleName("CUSTOMER")));
        return userService.add(user);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        String encodedPassword = passwordEncoder.encode(password);
        if (userFromDb.isEmpty() || !passwordEncoder.matches(password, encodedPassword)) {
            throw new AuthenticationException("Incorrect username or password!!!");
        }
        return userFromDb.get();
    }
}
