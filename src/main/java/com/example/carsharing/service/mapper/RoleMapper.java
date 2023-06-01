package com.example.carsharing.service.mapper;

import com.example.carsharing.model.UserRole;
import com.example.carsharing.dto.response.RoleResponseDto;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public RoleResponseDto mapToDto(UserRole role) {
        RoleResponseDto responseDto = new RoleResponseDto();
        responseDto.setId(role.getId());
        responseDto.setName(role.getRoleName().name());
        return responseDto;
    }
}
