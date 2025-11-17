package com.erp.inventory.dto;

public record InventoryDTO(
        Long id,
        ItemCategoryDTO category,
        String code,
        String name,
        Integer minimumStock,
        Long entryCount,
        Long outputCount,
        Long stock,
        Long pendingEntryCount,
        Long reservedOutput
) {
}
