package com.jardvcode.erp.inventory.dto;

import com.jardvcode.erp.inventory.domain.MovementEnum;
import com.jardvcode.erp.inventory.domain.StatusEnum;
import com.jardvcode.erp.inventory.entity.MovementEntity;

import java.time.LocalDateTime;

public record MovementDTO(
        Long id,
        InventoryDTO item,
        MovementEnum movement,
        Long quantity,
        String invoiceUrl,
        String outputReason,
        StatusEnum status,
        String rejectReason,
        String createdBy,
        LocalDateTime createdAt,
        String updatedBy,
        LocalDateTime updatedAt,
        String reviewedBy,
        LocalDateTime reviewedAt
) {

    public static MovementDTO fromEntity(MovementEntity entity) {
        return new MovementDTO(
                entity.getId(),
                InventoryDTO.fromEntity(entity.getItem()),
                entity.getType(),
                entity.getQuantity(),
                entity.getInvoiceUrl(),
                entity.getOutputReason(),
                entity.getStatus(),
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
