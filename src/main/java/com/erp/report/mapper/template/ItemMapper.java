package com.erp.report.mapper.template;

import com.erp.report.dto.template.ItemDTO;
import com.erp.report.entity.template.ItemEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class ItemMapper {
    public ItemEntity fromDTO(ItemDTO dto) {
        return ItemEntity.create(
                dto.id(),
                dto.uuid(),
                dto.label(),
                dto.position()
        );
    }

    public ItemDTO fromEntity(ItemEntity entity) {
        return new ItemDTO(
                entity.getId(),
                entity.getUuid().toString(),
                entity.getSection().getUuid().toString(),
                entity.getLabel(),
                entity.getPosition()
        );
    }

    public Set<ItemEntity> fromDTO(List<ItemDTO> dtos) {
        return dtos.stream()
                .map(dto -> fromDTO(dto))
                .collect(Collectors.toSet());
    }

    public List<ItemDTO> fromEntity(Set<ItemEntity> entities) {
        return entities.stream()
                .map(entity -> fromEntity(entity))
                .collect(Collectors.toList());
    }

}
