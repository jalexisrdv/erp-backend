package com.erp.inventory.dto;

import com.erp.inventory.domain.MovementEnum;

import java.time.LocalDateTime;

public record MovementDTO(
        Long id,
        InventoryDTO item,
        MovementEnum movement,
        Long quantity,
        String invoiceUrl,
        String outputReason,
        String status,
        String rejectReason,
        String createdBy,
        LocalDateTime createdAt,
        String updatedBy,
        LocalDateTime updatedAt,
        String reviewedBy,
        LocalDateTime reviewedAt
) {
}
