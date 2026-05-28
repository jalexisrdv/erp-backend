package com.jardvcode.erp.authorization.repository.permission;

import com.jardvcode.erp.authorization.entity.permission.PermissionViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionViewRepository extends JpaRepository<PermissionViewEntity, Long> {
    List<PermissionViewEntity> findByModuleId(Long id);
}
