package com.example.car_sharing_sertvice_project.model.dto;

import java.util.Set;
import lombok.Data;

@Data
public class UserRolesRequestDto {
    private Set<String> roles;
}
