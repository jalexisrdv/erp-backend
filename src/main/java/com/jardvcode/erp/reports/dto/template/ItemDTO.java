package com.jardvcode.erp.reports.dto.template;

import com.jardvcode.erp.reports.entity.template.ItemEntity;

import java.util.List;
import java.util.Set;

public record ItemDTO(
        Long id,
        String uuid,
        String sectionUuid,
        String label,
        Integer position
) {

    public static ItemDTO fromEntity(ItemEntity entity) {
        return new ItemDTO(
                entity.getId(),
                entity.getUuid().toString(),
                entity.getSection().getUuid().toString(),
                entity.getLabel(),
                entity.getPosition()
        );
    }

    public static List<ItemDTO> fromEntities(Set<ItemEntity> entities) {
        return entities.stream()
                .map(ItemDTO::fromEntity)
                .toList();
    }

}
