package com.erp.role.repository;

import com.erp.role.entity.RolePermissionViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionViewRepository extends JpaRepository<RolePermissionViewEntity, Long> {
    List<RolePermissionViewEntity> findByRoleId(Long id);
}
