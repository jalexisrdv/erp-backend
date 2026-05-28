package com.jardvcode.erp.reports.mapper.template;

import com.jardvcode.erp.reports.dto.template.TemplateCatalogDTO;
import com.jardvcode.erp.reports.entity.template.TemplateEntity;
import com.jardvcode.erp.shared.mapper.AbstractMapper;

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
