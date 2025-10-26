package com.erp.role.mapper;

import com.erp.permission.entity.PermissionEntity;
import com.erp.role.dto.PermissionDTO;
import com.erp.shared.mapper.AbstractMapper;

public final class PermissionMapper extends AbstractMapper<PermissionDTO, PermissionEntity> {
    @Override
    public PermissionEntity fromDTO(PermissionDTO dto) {
        return PermissionEntity.withId(
                dto.id()
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
