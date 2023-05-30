package com.example.car_sharing_sertvice_project.model.dto;

import java.util.List;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private List<RoleResponseDto> roles;
}
