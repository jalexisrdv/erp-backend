package com.erp.permission.mapper;

import com.erp.permission.dto.PermissionDTO;
import com.erp.permission.entity.PermissionEntity;
import com.erp.shared.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public final class PermissionMapper extends AbstractMapper<PermissionDTO, PermissionEntity> {
    @Override
    public PermissionEntity fromDTO(PermissionDTO dto) {
        return PermissionEntity.create(
                dto.id(),
                dto.name()
        );
    }

    @Override
    public PermissionDTO fromEntity(PermissionEntity entity) {
        return new PermissionDTO(
                entity.getId(),
                entity.getName()
        );
    }
}
