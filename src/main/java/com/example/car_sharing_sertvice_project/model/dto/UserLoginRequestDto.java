package com.example.car_sharing_sertvice_project.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequestDto {
    @NotBlank(message = "Login can't be null or blank!")
    private String login;
    @NotBlank(message = "Password can't be null or blank!")
    private String password;
}
