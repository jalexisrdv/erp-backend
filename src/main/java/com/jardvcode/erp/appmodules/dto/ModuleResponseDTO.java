package com.jardvcode.erp.appmodules.dto;

import com.jardvcode.erp.appmodules.entity.AppModuleViewEntity;

import java.util.List;

public record ModuleResponseDTO(
        Long id,
        String code,
        String name,
        Long parentId,
        String fullPath
) {

    public static ModuleResponseDTO fromEntity(AppModuleViewEntity entity) {
        return new ModuleResponseDTO(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getParentId(),
                entity.getFullPath()
        );
    }

    public static List<ModuleResponseDTO> fromEntities(List<AppModuleViewEntity> entities) {
        return entities.stream()
                .map(ModuleResponseDTO::fromEntity)
                .toList();
    }

}