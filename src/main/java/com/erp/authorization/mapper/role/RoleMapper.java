package com.erp.authorization.mapper.role;

import com.erp.authorization.dto.role.RoleDTO;
import com.erp.authorization.entity.role.RoleEntity;
import com.erp.shared.mapper.AbstractMapper;

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
