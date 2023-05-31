package com.example.carsharing.service.mapper;

import com.example.carsharing.dto.response.RoleResponseDto;
import com.example.carsharing.model.UserRole;
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
