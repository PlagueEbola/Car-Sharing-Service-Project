package com.example.carsharing.dto.request;

import java.util.Set;
import lombok.Data;

@Data
public class UserRolesRequestDto {
    private Set<String> roles;
}
