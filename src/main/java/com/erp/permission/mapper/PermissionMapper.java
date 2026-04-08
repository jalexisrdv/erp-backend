package com.erp.permission.mapper;

import com.erp.permission.dto.PermissionRequestDTO;
import com.erp.permission.dto.PermissionResponseDTO;
import com.erp.permission.entity.PermissionEntity;
import com.erp.permission.entity.PermissionViewEntity;

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

    public PermissionResponseDTO fromEntity(PermissionViewEntity entity) {
        return new PermissionResponseDTO(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getFullPath()
        );
    }

    public List<PermissionResponseDTO> fromEntity(List<PermissionViewEntity> entities) {
        return entities.stream()
                .map(entity -> fromEntity(entity))
                .toList();
    }

}
