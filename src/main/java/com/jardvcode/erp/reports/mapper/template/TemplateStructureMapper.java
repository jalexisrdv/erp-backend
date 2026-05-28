package com.jardvcode.erp.reports.mapper.template;

import com.jardvcode.erp.reports.dto.template.TemplateStructureResponseDTO;
import com.jardvcode.erp.reports.entity.template.TemplateEntity;

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
