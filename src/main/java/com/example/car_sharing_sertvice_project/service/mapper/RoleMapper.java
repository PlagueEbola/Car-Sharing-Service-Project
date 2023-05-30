package com.example.car_sharing_sertvice_project.service.mapper;

import com.example.car_sharing_sertvice_project.model.Role;
import com.example.car_sharing_sertvice_project.model.dto.RoleResponseDto;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public RoleResponseDto mapToDto(Role role) {
        RoleResponseDto responseDto = new RoleResponseDto();
        responseDto.setId(role.getId());
        responseDto.setName(role.getRoleName().name());
        return responseDto;
    }
}
