package com.erp.user.mapper;

import com.erp.role.entity.RoleEntity;
import com.erp.shared.mapper.AbstractMapper;
import com.erp.user.dto.RoleDTO;

public final class RoleMapper extends AbstractMapper<RoleDTO, RoleEntity> {
    @Override
    public RoleEntity fromDTO(RoleDTO dto) {
        return RoleEntity.withId(
                dto.id()
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
