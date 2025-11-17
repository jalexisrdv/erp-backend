package com.erp.inventory.repository;

import com.erp.inventory.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InventoryMovementRepository extends JpaRepository<MovementEntity, Long>, JpaSpecificationExecutor {
    
}
