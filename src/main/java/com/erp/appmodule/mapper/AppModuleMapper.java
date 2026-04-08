package com.erp.appmodule.mapper;

import com.erp.appmodule.dto.ModuleRequestDTO;
import com.erp.appmodule.dto.ModuleResponseDTO;
import com.erp.appmodule.entity.AppModuleEntity;
import com.erp.appmodule.entity.AppModuleViewEntity;

import java.util.List;

public final class AppModuleMapper {

    public AppModuleEntity fromDTO(ModuleRequestDTO dto) {
        return AppModuleEntity.create(
                dto.id(),
                dto.code(),
                dto.name(),
                dto.parentId()
        );
    }

    public ModuleResponseDTO fromEntity(AppModuleViewEntity entity) {
        return new ModuleResponseDTO(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getParentId(),
                entity.getFullPath()
        );
    }

    public List<ModuleResponseDTO> fromEntity(List<AppModuleViewEntity> entities) {
        return entities.stream()
                .map(entity -> fromEntity(entity))
                .toList();
    }

}
