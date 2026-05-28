package com.jardvcode.erp.authorization.repository.permission;

import com.jardvcode.erp.authorization.entity.permission.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
}
