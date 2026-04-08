package com.erp.role.mapper;

import com.erp.permission.entity.PermissionEntity;
import com.erp.role.dto.PermissionRequestDTO;
import com.erp.role.dto.PermissionResponseDTO;
import com.erp.role.entity.RolePermissionViewEntity;

import java.util.List;

public final class PermissionMapper {

    public PermissionEntity fromDTO(PermissionRequestDTO dto) {
        return PermissionEntity.create(
                dto.id(),
                dto.moduleId(),
                dto.code(),
                dto.name(),
                dto.description()
        );
    }

    public PermissionResponseDTO fromEntity(RolePermissionViewEntity entity) {
        return new PermissionResponseDTO(
                entity.getId(),
                entity.getModuleId(),
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getFullPath()
        );
    }

    public List<PermissionEntity> fromDTO(List<PermissionRequestDTO> dtos) {
        return dtos.stream()
                .map(dto -> fromDTO(dto))
                .toList();
    }

    public List<PermissionResponseDTO> fromEntity(List<RolePermissionViewEntity> entities) {
        return entities.stream()
                .map(entity -> fromEntity(entity))
                .toList();
    }

}
