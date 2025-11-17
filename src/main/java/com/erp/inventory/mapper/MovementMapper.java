package com.erp.inventory.mapper;

import com.erp.inventory.dto.MovementDTO;
import com.erp.inventory.entity.MovementEntity;
import com.erp.shared.mapper.AbstractMapper;

public final class MovementMapper extends AbstractMapper<MovementDTO, MovementEntity> {

    private final InventoryMapper inventoryMapper;

    public MovementMapper() {
        this.inventoryMapper = new InventoryMapper();
    }

    @Override
    public MovementEntity fromDTO(MovementDTO dto) {
        return MovementEntity.fromPrimitives(
                dto.id(),
                dto.item().id(),
                dto.invoiceUrl(),
                dto.quantity(),
                dto.outputReason(),
                dto.rejectReason()
        );
    }

    @Override
    public MovementDTO fromEntity(MovementEntity entity) {
        return new MovementDTO(
                entity.getId(),
                inventoryMapper.fromEntity(entity.getItem()),
                entity.getType(),
                entity.getQuantity(),
                entity.getInvoiceUrl(),
                entity.getOutputReason(),
                entity.getStatus().name(),
                entity.getRejectReason(),
                entity.createdBy(),
                entity.getCreatedAt(),
                entity.updatedBy(),
                entity.getUpdatedAt(),
                entity.reviewedBy(),
                entity.getReviewedAt()
        );
    }
}
