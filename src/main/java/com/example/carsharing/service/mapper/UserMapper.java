package com.example.carsharing.service.mapper;

import com.example.carsharing.dto.request.UserRequestDto;
import com.example.carsharing.dto.response.RoleResponseDto;
import com.example.carsharing.dto.response.UserResponseDto;
import com.example.carsharing.model.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final RoleMapper roleMapper;

    public UserResponseDto mapToDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPassword(user.getPassword());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        List<RoleResponseDto> roles = user.getRoles()
                .stream()
                .map(roleMapper::mapToDto)
                .toList();
        userResponseDto.setRoles(roles);
        return userResponseDto;
    }

    public User mapToModel(UserRequestDto requestDto) {
        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        return user;
    }
}
