package com.jardvcode.erp.authorization.mapper.role;

import com.jardvcode.erp.authorization.dto.role.RoleDTO;
import com.jardvcode.erp.authorization.entity.role.RoleEntity;
import com.jardvcode.erp.shared.mapper.AbstractMapper;

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
