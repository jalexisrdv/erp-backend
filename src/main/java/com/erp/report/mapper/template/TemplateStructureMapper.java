package com.erp.report.mapper.template;

import com.erp.report.dto.template.TemplateStructureResponseDTO;
import com.erp.report.entity.template.TemplateEntity;

public final class TemplateStructureMapper {

    private final SectionMapper sectionMapper;

    public TemplateStructureMapper() {
        sectionMapper = new SectionMapper();
    }

    public TemplateStructureResponseDTO fromEntity(TemplateEntity entity) {
        return new TemplateStructureResponseDTO(
                entity.getId(),
                entity.getName(),
                sectionMapper.fromEntity(entity.getSections())
        );
    }
}
