package com.jardvcode.erp.inventory.dto;

import com.jardvcode.erp.inventory.entity.ItemCategoryEntity;

import java.util.List;

public record ItemCategoryDTO(
        Long id,
        String name
) {

    public static ItemCategoryDTO fromEntity(ItemCategoryEntity entity) {
        return new ItemCategoryDTO(
                entity.getId(),
                entity.getName()
        );
    }

    public static List<ItemCategoryDTO> fromEntities(List<ItemCategoryEntity> entities) {
        return entities.stream()
                .map(ItemCategoryDTO::fromEntity)
                .toList();
    }

}
