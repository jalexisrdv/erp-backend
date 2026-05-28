package com.jardvcode.erp.authorization.mapper.role;

import com.jardvcode.erp.authorization.entity.permission.PermissionEntity;
import com.jardvcode.erp.authorization.dto.role.PermissionDTO;
import com.jardvcode.erp.authorization.entity.role.RolePermissionViewEntity;

import java.util.List;

public final class PermissionMapper {

    public PermissionEntity fromDTO(PermissionDTO dto) {
        return PermissionEntity.create(
                dto.id(),
                dto.moduleId(),
                dto.code(),
                dto.name(),
                dto.description()
        );
    }

    public PermissionDTO fromEntity(RolePermissionViewEntity entity) {
        return new PermissionDTO(
                entity.getId(),
                entity.getModuleId(),
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getFullPath()
        );
    }

    public List<PermissionEntity> fromDTO(List<PermissionDTO> dtos) {
        return dtos.stream()
                .map(dto -> fromDTO(dto))
                .toList();
    }

    public List<PermissionDTO> fromEntity(List<RolePermissionViewEntity> entities) {
        return entities.stream()
                .map(entity -> fromEntity(entity))
                .toList();
    }

}
