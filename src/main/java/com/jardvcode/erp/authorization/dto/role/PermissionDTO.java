package com.jardvcode.erp.authorization.dto.role;

import com.jardvcode.erp.authorization.entity.role.RolePermissionViewEntity;

import java.util.List;

public record PermissionDTO(
        Long id,
        Long moduleId,
        String code,
        String name,
        String description,
        String fullPath
) {
    public static PermissionDTO fromEntity(RolePermissionViewEntity entity) {
        return new PermissionDTO(
                entity.getId(),
                entity.getModuleId(),
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getFullPath()
        );
    }

    public static List<PermissionDTO> fromEntities(List<RolePermissionViewEntity> entities) {
        return entities.stream()
                .map(PermissionDTO::fromEntity)
                .toList();
    }

}
