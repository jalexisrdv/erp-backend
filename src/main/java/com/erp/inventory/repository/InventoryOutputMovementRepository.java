package com.erp.inventory.repository;

import com.erp.inventory.entity.InventoryEntryMovementEntity;
import com.erp.inventory.entity.InventoryOutputMovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface InventoryOutputMovementRepository extends JpaRepository<InventoryOutputMovementEntity, Long>, JpaSpecificationExecutor {
    Optional<InventoryEntryMovementEntity> findByInventoryId(Long inventoryId);
}
