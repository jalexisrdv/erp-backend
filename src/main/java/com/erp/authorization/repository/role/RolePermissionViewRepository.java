package com.erp.authorization.repository.role;

import com.erp.authorization.entity.role.RolePermissionViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionViewRepository extends JpaRepository<RolePermissionViewEntity, Long> {
    List<RolePermissionViewEntity> findByRoleId(Long id);
}
