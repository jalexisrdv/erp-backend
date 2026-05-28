package com.jardvcode.erp.inventory.repository;

import com.jardvcode.erp.inventory.entity.InventoryEntity;
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
        SET item.entryCount = item.entryCount + :quantity
        WHERE item.id = :id
    """)
    int increaseEntryCount(Long id, Long quantity);

    @Modifying
    @Query("""
        UPDATE InventoryEntity item
        SET item.pendingEntryCount = item.pendingEntryCount + :quantity
        WHERE item.id = :id
    """)
    int increasePendingEntryCount(Long id, Long quantity);

    @Modifying
    @Query("""
        UPDATE InventoryEntity item
        SET item.pendingEntryCount = item.pendingEntryCount - :quantity
        WHERE item.id = :id
    """)
    int decreasePendingEntryCount(Long id, Long quantity);

    @Modifying
    @Query("""
        UPDATE InventoryEntity item
        SET item.outputCount = item.outputCount + :quantity
        WHERE item.id = :id
    """)
    int increaseOutputCount(Long id, Long quantity);

    @Modifying
    @Query("""
        UPDATE InventoryEntity item
        SET item.reservedOutputCount = item.reservedOutputCount + :quantity
        WHERE item.id = :id
    """)
    int increaseReservedOutputCount(Long id, Long quantity);

    @Modifying
    @Query("""
        UPDATE InventoryEntity item
        SET item.reservedOutputCount = item.reservedOutputCount - :quantity
        WHERE item.id = :id
    """)
    int decreaseReservedOutputCount(Long id, Long quantity);

}
