package com.jardvcode.erp.reports.dto.template;

import com.jardvcode.erp.reports.entity.template.SectionEntity;

import java.util.List;
import java.util.Set;

public record SectionDTO(
        Long id,
        String uuid,
        Long templateId,
        String name,
        List<ItemDTO> items,
        Integer position
) {

    public static SectionDTO fromEntity(SectionEntity entity) {
        return new SectionDTO(
                entity.getId(),
                entity.getUuid().toString(),
                entity.getTemplate().getId(),
                entity.getName(),
                ItemDTO.fromEntities(entity.getItems()),
                entity.getPosition()
        );
    }

    public static List<SectionDTO> fromEntities(Set<SectionEntity> entities) {
        return entities.stream()
                .map(SectionDTO::fromEntity)
                .toList();
    }

}
