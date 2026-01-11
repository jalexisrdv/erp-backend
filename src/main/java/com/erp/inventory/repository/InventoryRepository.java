package com.erp.inventory.repository;

import com.erp.inventory.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Long>, JpaSpecificationExecutor {
    Optional<InventoryEntity> findByItemName(String itemName);

    @Modifying
    @Query("""
        UPDATE InventoryEntity item
        SET item.pendingEntryCount = (SELECT COALESCE(SUM(movement.quantity), 0) FROM MovementEntity movement WHERE movement.item.id = :id AND movement.type = 'ENTRADA' AND movement.status = 'PENDIENTE')
        WHERE item.id = :id
    """)
    int updatePendingEntryCount(Long id);

    @Modifying
    @Query("""
        UPDATE InventoryEntity item
        SET item.entryCount = (SELECT COALESCE(SUM(movement.quantity), 0) FROM MovementEntity movement WHERE movement.item.id = :id AND movement.type = 'ENTRADA' AND movement.status <> 'PENDIENTE')
        WHERE item.id = :id
    """)
    int updateEntryCount(Long id);

    @Modifying
    @Query("""
        UPDATE InventoryEntity item
        SET item.outputCount = (SELECT COALESCE(SUM(movement.quantity), 0) FROM MovementEntity movement WHERE movement.item.id = :id AND movement.type = 'SALIDA' AND movement.status <> 'PENDIENTE')
        WHERE item.id = :id
    """)
    int updateOutputCount(Long id);

    @Modifying
    @Query("""
        UPDATE InventoryEntity item
        SET item.reservedOutputCount = (SELECT COALESCE(SUM(movement.quantity), 0) FROM MovementEntity movement WHERE movement.item.id = :id AND movement.type = 'SALIDA' AND movement.status = 'PENDIENTE')
        WHERE item.id = :id
    """)
    int updateReservedOutputCount(Long id);

}
