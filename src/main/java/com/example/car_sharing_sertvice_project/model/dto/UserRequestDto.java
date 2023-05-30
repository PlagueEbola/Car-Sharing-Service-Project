package com.example.car_sharing_sertvice_project.model.dto;

import com.example.car_sharing_sertvice_project.lib.validation.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {
    @ValidEmail
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
