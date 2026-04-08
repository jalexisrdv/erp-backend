package com.erp.appmodule.repository;

import com.erp.appmodule.entity.AppModuleViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppModuleViewRepository extends JpaRepository<AppModuleViewEntity, Long> {
    List<AppModuleViewEntity> findByParentIdNotNull();
}
