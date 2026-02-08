package com.erp.report.mapper.template;

import com.erp.report.dto.template.TemplateCatalogDTO;
import com.erp.report.entity.template.TemplateEntity;
import com.erp.shared.mapper.AbstractMapper;

public final class TemplateCatalogMapper extends AbstractMapper<TemplateCatalogDTO, TemplateEntity> {
    @Override
    public TemplateEntity fromDTO(TemplateCatalogDTO dto) {
        return null;
    }

    @Override
    public TemplateCatalogDTO fromEntity(TemplateEntity entity) {
        return new TemplateCatalogDTO(
                entity.getId(),
                entity.getName()
        );
    }
}
