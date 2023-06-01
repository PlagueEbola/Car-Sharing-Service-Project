package com.example.carsharing.dto.request;

import com.example.carsharing.lib.validation.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {
    @ValidEmail
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
