package com.jardvcode.erp.users.dto;

import com.jardvcode.erp.authorization.entity.role.RoleEntity;

import java.util.List;

public record RoleDTO(
        Long id,
        String name
) {

    public static RoleDTO fromEntity(RoleEntity entity) {
        return new RoleDTO(
                entity.getId(),
                entity.getName()
        );
    }

    public static List<RoleDTO> fromEntities(List<RoleEntity> entities) {
        return entities.stream()
                .map(RoleDTO::fromEntity)
                .toList();
    }

}
