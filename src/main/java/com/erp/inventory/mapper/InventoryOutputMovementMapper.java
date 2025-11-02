package com.erp.inventory.mapper;

import com.erp.inventory.dto.InventoryOutputMovementDTO;
import com.erp.inventory.entity.InventoryOutputMovementEntity;
import com.erp.shared.mapper.AbstractMapper;

public final class InventoryOutputMovementMapper extends AbstractMapper<InventoryOutputMovementDTO, InventoryOutputMovementEntity> {
    @Override
    public InventoryOutputMovementEntity fromDTO(InventoryOutputMovementDTO dto) {
        return InventoryOutputMovementEntity.create(
                dto.id(),
                dto.inventoryId(),
                dto.quantity(),
                dto.outputReason(),
                dto.rejectReason()
        );
    }

    @Override
    public InventoryOutputMovementDTO fromEntity(InventoryOutputMovementEntity entity) {
        return new InventoryOutputMovementDTO(
                entity.getId(),
                entity.getInventoryId(),
                entity.getQuantity(),
                entity.getOutputReason(),
                entity.getStatus().name(),
                entity.getRejectReason()
        );
    }
}
