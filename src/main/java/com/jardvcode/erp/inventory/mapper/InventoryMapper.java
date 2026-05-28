package com.jardvcode.erp.inventory.mapper;

import com.jardvcode.erp.inventory.dto.InventoryDTO;
import com.jardvcode.erp.inventory.dto.ItemCategoryDTO;
import com.jardvcode.erp.inventory.entity.InventoryEntity;
import com.jardvcode.erp.shared.mapper.AbstractMapper;

public final class InventoryMapper extends AbstractMapper<InventoryDTO, InventoryEntity> {
    @Override
    public InventoryEntity fromDTO(InventoryDTO dto) {
        return InventoryEntity.create(
                dto.id(),
                dto.category().id(),
                dto.category().name(),
                dto.code(),
                dto.name(),
                dto.minimumStock()
        );
    }

    @Override
    public InventoryDTO fromEntity(InventoryEntity entity) {
        return new InventoryDTO(
                entity.getId(),
                new ItemCategoryDTO(entity.getCategory().getId(), entity.getCategory().getName()),
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
