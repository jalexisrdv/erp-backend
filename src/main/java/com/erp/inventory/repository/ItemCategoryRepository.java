package com.erp.inventory.repository;

import com.erp.inventory.entity.ItemCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ItemCategoryRepository extends JpaRepository<ItemCategoryEntity, Long>, JpaSpecificationExecutor {
    Optional<ItemCategoryEntity> findByName(String name);
}
