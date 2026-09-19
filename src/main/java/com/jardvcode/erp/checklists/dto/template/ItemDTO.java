package com.jardvcode.erp.checklists.dto.template;

import com.jardvcode.erp.checklists.entity.template.TemplateItemEntity;

import java.util.List;
import java.util.Set;

public record ItemDTO(
        Long id,
        String uuid,
        String sectionUuid,
        String label,
        Integer position
) {

    public static ItemDTO fromEntity(TemplateItemEntity entity) {
        return new ItemDTO(
                entity.getId(),
                entity.getUuid().toString(),
                entity.getSection().getUuid().toString(),
                entity.getLabel(),
                entity.getPosition()
        );
    }

    public static List<ItemDTO> fromEntities(Set<TemplateItemEntity> entities) {
        return entities.stream()
                .map(ItemDTO::fromEntity)
                .toList();
    }

}
