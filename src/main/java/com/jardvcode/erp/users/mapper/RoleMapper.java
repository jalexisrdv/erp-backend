package com.jardvcode.erp.users.mapper;

import com.jardvcode.erp.authorization.entity.role.RoleEntity;
import com.jardvcode.erp.shared.mapper.AbstractMapper;
import com.jardvcode.erp.users.dto.RoleDTO;

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
