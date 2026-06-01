package com.jardvcode.erp.users.dto;

import com.jardvcode.erp.users.entity.UserEntity;

import java.util.List;

public record UserCatalogDTO(
        Long id,
        String name
) {

    public static UserCatalogDTO fromEntity(UserEntity entity) {
        return new UserCatalogDTO(
                entity.getId(),
                entity.fullName()
        );
    }

    public static List<UserCatalogDTO> fromEntities(List<UserEntity> entities) {
        return entities.stream()
                .map(UserCatalogDTO::fromEntity)
                .toList();
    }

}
