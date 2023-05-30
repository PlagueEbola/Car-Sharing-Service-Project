package com.example.car_sharing_sertvice_project.dtos.mapper;

import com.example.car_sharing_sertvice_project.dtos.request.UserRequestDto;
import com.example.car_sharing_sertvice_project.dtos.response.UserResponseDto;
import com.example.car_sharing_sertvice_project.model.User;

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
        responseDto.setId(Long.valueOf(user.getId()));
        return responseDto;
    }
}
