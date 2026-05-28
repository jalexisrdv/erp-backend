package com.jardvcode.erp.users.mapper;

import com.jardvcode.erp.shared.mapper.AbstractMapper;
import com.jardvcode.erp.users.dto.UserCatalogDTO;
import com.jardvcode.erp.users.entity.UserEntity;

public final class UserCatalogMapper extends AbstractMapper<UserCatalogDTO, UserEntity> {
    @Override
    public UserEntity fromDTO(UserCatalogDTO dto) {
        return null;
    }

    @Override
    public UserCatalogDTO fromEntity(UserEntity entity) {
        return new UserCatalogDTO(
                entity.getId(),
                entity.fullName()
        );
    }
}
