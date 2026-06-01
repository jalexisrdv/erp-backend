package com.jardvcode.erp.authorization.dto.permission;

import com.jardvcode.erp.authorization.entity.permission.PermissionViewEntity;

import java.util.List;

public record PermissionResponseDTO(
        Long id,
        String code,
        String name,
        String description,
        String fullPath
) {

    public static PermissionResponseDTO fromEntity(PermissionViewEntity entity) {
        return new PermissionResponseDTO(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getFullPath()
        );
    }

    public static List<PermissionResponseDTO> fromEntities(List<PermissionViewEntity> entities) {
        return entities.stream()
                .map(PermissionResponseDTO::fromEntity)
                .toList();
    }

}
