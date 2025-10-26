package com.erp.role.mapper;

import com.erp.role.dto.RoleDTO;
import com.erp.role.entity.RoleEntity;
import com.erp.shared.mapper.AbstractMapper;

import java.util.List;

public final class RoleMapper extends AbstractMapper<RoleDTO, RoleEntity> {
    @Override
    public RoleEntity fromDTO(RoleDTO dto) {
        return RoleEntity.create(
                dto.id(),
                dto.name()
        );
    }

    @Override
    public RoleDTO fromEntity(RoleEntity entity) {
        return new RoleDTO(
                entity.getId(),
                entity.getName()
        );
    }
}
