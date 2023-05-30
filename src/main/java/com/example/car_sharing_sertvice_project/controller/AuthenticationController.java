package com.example.car_sharing_sertvice_project.controller;

import com.example.car_sharing_sertvice_project.exception.AuthenticationException;
import com.example.car_sharing_sertvice_project.model.User;
import com.example.car_sharing_sertvice_project.model.dto.UserLoginRequestDto;
import com.example.car_sharing_sertvice_project.model.dto.UserRequestDto;
import com.example.car_sharing_sertvice_project.model.dto.UserResponseDto;
import com.example.car_sharing_sertvice_project.security.AuthenticationService;
import com.example.car_sharing_sertvice_project.security.jwt.JwtTokenProvider;
import com.example.car_sharing_sertvice_project.service.mapper.UserMapper;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto userLoginDto)
            throws AuthenticationException {
        User user = authenticationService.login(userLoginDto.getLogin(),
                userLoginDto.getPassword());
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toList()));
        Map<Object, Object> response = new HashMap<>();
        response.put("email", userLoginDto.getLogin());
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRequestDto requestDto) {
        User user = authenticationService.register(
                requestDto.getEmail(),
                requestDto.getPassword(),
                requestDto.getFirstName(),
                requestDto.getLastName()
        );
        return userMapper.mapToDto(user);
    }
}
