package com.erp.inventory.dto;

public record InventoryOutputMovementDTO(
        Long id,
        Long inventoryId,
        Integer quantity,
        String outputReason,
        String status,
        String rejectReason
) {
}
