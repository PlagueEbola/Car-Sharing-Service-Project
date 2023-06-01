package com.example.carsharing.controller;

import com.example.carsharing.dto.request.UserRequestDto;
import com.example.carsharing.dto.request.UserRolesRequestDto;
import com.example.carsharing.dto.response.UserResponseDto;
import com.example.carsharing.model.User;
import com.example.carsharing.model.UserRole;
import com.example.carsharing.service.RoleService;
import com.example.carsharing.service.UserService;
import com.example.carsharing.service.mapper.UserMapper;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @GetMapping("/me")
    public UserResponseDto get(Authentication auth) {
        User user = getAuthenticated(auth);
        return userMapper.mapToDto(user);
    }

    @PutMapping("/me")
    public UserResponseDto update(Authentication auth, @RequestBody UserRequestDto requestDto) {
        User user = userMapper.mapToModel(requestDto);
        User authenticatedUser = getAuthenticated(auth);
        user.setId(authenticatedUser.getId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(authenticatedUser.getRoles());
        userService.update(user);
        return userMapper.mapToDto(user);
    }

    @PutMapping("/{id}/role")
    public UserResponseDto updateRoles(
            @PathVariable Long id,
            @RequestBody UserRolesRequestDto rolesRequest) {
        User user = userService.findById(id).orElseThrow(
                () -> new RuntimeException("User with id " + id + " not found"));
        Set<UserRole> newUserRoles = rolesRequest.getRoles().stream()
                .map(roleService::findByRoleName)
                .collect(Collectors.toSet());
        user.setRoles(newUserRoles);

        userService.update(user);
        return userMapper.mapToDto(user);
    }

    private User getAuthenticated(Authentication auth) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        String email = details.getUsername();
        return userService.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User with email " + email + " not found"));
    }
}
