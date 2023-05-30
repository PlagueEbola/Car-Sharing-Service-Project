package com.example.car_sharing_sertvice_project.controller;

import com.example.car_sharing_sertvice_project.model.Role;
import com.example.car_sharing_sertvice_project.model.User;
import com.example.car_sharing_sertvice_project.model.dto.UserRequestDto;
import com.example.car_sharing_sertvice_project.model.dto.UserResponseDto;
import com.example.car_sharing_sertvice_project.model.dto.UserRolesRequestDto;
import com.example.car_sharing_sertvice_project.service.RoleService;
import com.example.car_sharing_sertvice_project.service.UserService;
import com.example.car_sharing_sertvice_project.service.mapper.UserMapper;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
        Set<Role> newUserRoles = rolesRequest.getRoles().stream()
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
