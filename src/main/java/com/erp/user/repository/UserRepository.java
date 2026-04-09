package com.erp.user.repository;

import java.util.Optional;

import com.erp.user.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor {

    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    Optional<UserEntity> findWithRolesAndPermissionsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUsernameAndToken(String username, String token);

}