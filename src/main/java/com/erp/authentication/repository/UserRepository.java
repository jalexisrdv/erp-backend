package com.erp.authentication.repository;

import java.util.Optional;

import com.erp.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor {

    public Optional<UserEntity> findByUsername(String username);
    public Optional<UserEntity> findByUsernameAndToken(String username, String token);

}