package com.erp.user.mapper;

import com.erp.shared.mapper.AbstractMapper;
import com.erp.user.dto.UserCatalogDTO;
import com.erp.user.entity.UserEntity;

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
