package com.erp.report.mapper.template;

import com.erp.report.dto.template.SectionDTO;
import com.erp.report.entity.template.SectionEntity;
import com.erp.shared.mapper.AbstractMapper;

public final class SectionMapper extends AbstractMapper<SectionDTO, SectionEntity> {
    @Override
    public SectionEntity fromDTO(SectionDTO dto) {
        return SectionEntity.create(
                dto.id(),
                dto.templateId(),
                dto.name()
        );
    }

    @Override
    public SectionDTO fromEntity(SectionEntity entity) {
        return new SectionDTO(
                entity.getId(),
                entity.getTemplateId(),
                entity.getName()
        );
    }
}
