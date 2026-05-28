package com.jardvcode.erp.appmodules.repository;

import com.jardvcode.erp.appmodules.entity.AppModuleViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppModuleViewRepository extends JpaRepository<AppModuleViewEntity, Long> {
    List<AppModuleViewEntity> findByParentIdNotNull();
}
