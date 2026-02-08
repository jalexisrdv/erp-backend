package com.erp.report.mapper.template;

import com.erp.report.dto.template.TemplateDTO;
import com.erp.report.entity.template.TemplateEntity;
import com.erp.shared.mapper.AbstractMapper;

public final class TemplateMapper extends AbstractMapper<TemplateDTO, TemplateEntity> {
    @Override
    public TemplateEntity fromDTO(TemplateDTO dto) {
        return TemplateEntity.create(
                dto.id(),
                dto.name()
        );
    }

    @Override
    public TemplateDTO fromEntity(TemplateEntity entity) {
        return new TemplateDTO(
                entity.getId(),
                entity.getName()
        );
    }
}
