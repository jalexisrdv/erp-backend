package com.erp.inventory.repository;

import com.erp.inventory.entity.InventoryEntryMovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface InventoryEntryMovementRepository extends JpaRepository<InventoryEntryMovementEntity, Long>, JpaSpecificationExecutor {
    Optional<InventoryEntryMovementEntity> findByInventoryId(Long inventoryId);
}
