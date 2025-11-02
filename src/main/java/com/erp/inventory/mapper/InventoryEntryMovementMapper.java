package com.erp.inventory.mapper;

import com.erp.inventory.dto.InventoryEntryMovementDTO;
import com.erp.inventory.entity.InventoryEntryMovementEntity;
import com.erp.shared.mapper.AbstractMapper;

public final class InventoryEntryMovementMapper extends AbstractMapper<InventoryEntryMovementDTO, InventoryEntryMovementEntity> {
    @Override
    public InventoryEntryMovementEntity fromDTO(InventoryEntryMovementDTO dto) {
        return InventoryEntryMovementEntity.create(
                dto.id(),
                dto.inventoryId(),
                dto.quantity(),
                dto.rejectReason()
        );
    }

    @Override
    public InventoryEntryMovementDTO fromEntity(InventoryEntryMovementEntity entity) {
        return new InventoryEntryMovementDTO(
                entity.getId(),
                entity.getInventoryId(),
                entity.getQuantity(),
                entity.getStatus().name(),
                entity.getRejectReason()
        );
    }
}
