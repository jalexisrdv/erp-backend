package com.erp.user.mapper;

import com.erp.shared.mapper.AbstractMapper;
import com.erp.user.dto.UserDTO;
import com.erp.user.entity.UserEntity;

public final class UserMapper extends AbstractMapper<UserDTO, UserEntity> {
    @Override
    public UserEntity fromDTO(UserDTO dto) {
        return UserEntity.create(
                dto.id(),
                dto.firstName(), dto.middleName(),
                dto.lastName(), dto.secondLastName(),
                dto.phoneNumber(),
                dto.username()
        );
    }

    @Override
    public UserDTO fromEntity(UserEntity entity) {
        return new UserDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getMiddleName(),
                entity.getLastName(),
                entity.getSecondLastName(),
                entity.getPhoneNumber(),
                entity.getUsername()
        );
    }
}
