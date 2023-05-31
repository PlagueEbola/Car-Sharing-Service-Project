package com.example.carsharing.service.mapper;

import com.example.carsharing.dto.request.UserRequestDto;
import com.example.carsharing.dto.response.UserResponseDto;
import com.example.carsharing.model.User;

public class UserMapper {
    public User toModel(UserRequestDto requestDto) {
        User user = new User();
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        return user;
    }

    public UserResponseDto toResponseDto(User user) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setFirstName(user.getFirstName());
        responseDto.setLastName(user.getLastName());
        responseDto.setEmail(user.getEmail());
        responseDto.setId(user.getId());
        return responseDto;
    }
}
