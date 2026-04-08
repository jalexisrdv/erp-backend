package com.erp.permission.repository;

import com.erp.permission.entity.PermissionViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionViewRepository extends JpaRepository<PermissionViewEntity, Long> {
    List<PermissionViewEntity> findByModuleId(Long id);
}
