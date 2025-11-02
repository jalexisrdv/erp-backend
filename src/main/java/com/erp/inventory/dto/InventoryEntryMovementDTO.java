package com.erp.inventory.dto;

public record InventoryEntryMovementDTO(
        Long id,
        Long inventoryId,
        Integer quantity,
        String status,
        String rejectReason
) {
}
