package com.example.car_sharing_sertvice_project.dtos.response;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}
