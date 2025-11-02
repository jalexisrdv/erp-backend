package com.erp.inventory.mapper;

import com.erp.inventory.dto.ItemCategoryDTO;
import com.erp.inventory.entity.ItemCategoryEntity;
import com.erp.shared.mapper.AbstractMapper;

public final class ItemCategoryMapper extends AbstractMapper<ItemCategoryDTO, ItemCategoryEntity> {
    @Override
    public ItemCategoryEntity fromDTO(ItemCategoryDTO dto) {
        return ItemCategoryEntity.create(
                dto.id(),
                dto.name()
        );
    }

    @Override
    public ItemCategoryDTO fromEntity(ItemCategoryEntity entity) {
        return new ItemCategoryDTO(
                entity.getId(),
                entity.getName()
        );
    }
}
