package com.erp.inventory.mapper;

import com.erp.inventory.dto.InventoryDTO;
import com.erp.inventory.entity.InventoryEntity;
import com.erp.shared.mapper.AbstractMapper;

public final class InventoryMapper extends AbstractMapper<InventoryDTO, InventoryEntity> {
    @Override
    public InventoryEntity fromDTO(InventoryDTO dto) {
        return InventoryEntity.create(
                dto.id(),
                dto.itemCategoryId(),
                dto.itemCode(),
                dto.itemName(),
                dto.minimumStock()
        );
    }

    @Override
    public InventoryDTO fromEntity(InventoryEntity entity) {
        return new InventoryDTO(
                entity.getId(),
                entity.getItemCategoryId(),
                entity.getItemCode(),
                entity.getItemName(),
                entity.getMinimumStock()
        );
    }
}
