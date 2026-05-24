package com.erp.authorization.repository.permission;

import com.erp.authorization.entity.permission.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
}
