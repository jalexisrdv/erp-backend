package com.jardvcode.erp.inventory.dto;

import com.jardvcode.erp.inventory.entity.InventoryEntity;

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

    public static InventoryDTO fromEntity(InventoryEntity entity) {
        return new InventoryDTO(
                entity.getId(),
                ItemCategoryDTO.fromEntity(entity.getCategory()),
                entity.getItemCode(),
                entity.getItemName(),
                entity.getMinimumStock(),
                entity.getEntryCount(),
                entity.getOutputCount(),
                entity.stock(),
                entity.getPendingEntryCount(),
                entity.getReservedOutputCount()
        );
    }

}
