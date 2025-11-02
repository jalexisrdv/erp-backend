package com.erp.inventory.repository;

import com.erp.inventory.entity.InventoryEntity;
import com.erp.inventory.entity.ItemCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Long>, JpaSpecificationExecutor {
    Optional<InventoryEntity> findByItemName(String itemName);
}
