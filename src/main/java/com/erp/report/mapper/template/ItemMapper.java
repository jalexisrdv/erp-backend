package com.erp.report.mapper.template;

import com.erp.report.dto.template.ItemDTO;
import com.erp.report.entity.template.ItemEntity;
import com.erp.shared.mapper.AbstractMapper;

public final class ItemMapper extends AbstractMapper<ItemDTO, ItemEntity> {
    @Override
    public ItemEntity fromDTO(ItemDTO dto) {
        return ItemEntity.create(
                dto.id(),
                dto.sectionId(),
                dto.label()
        );
    }

    @Override
    public ItemDTO fromEntity(ItemEntity entity) {
        return new ItemDTO(
                entity.getId(),
                entity.getSection().getId(),
                entity.getLabel()
        );
    }
}
