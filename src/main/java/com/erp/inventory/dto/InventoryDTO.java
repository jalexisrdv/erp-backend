package com.erp.inventory.dto;

public record InventoryDTO(
        Long id,
        Long itemCategoryId,
        String itemCode,
        String itemName,
        Integer minimumStock
) {
}
