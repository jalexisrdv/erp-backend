package com.jardvcode.erp.reports.mapper.template;

import com.jardvcode.erp.reports.dto.template.SectionDTO;
import com.jardvcode.erp.reports.dto.template.TemplateDTO;
import com.jardvcode.erp.reports.entity.template.TemplateEntity;
import com.jardvcode.erp.shared.mapper.AbstractMapper;

import java.util.List;

public final class TemplateMapper extends AbstractMapper<TemplateDTO, TemplateEntity> {

    private final SectionMapper sectionMapper;

    public TemplateMapper() {
        sectionMapper = new SectionMapper();
    }

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

    public TemplateEntity fromSectionDTO(Long templateId, List<SectionDTO> dtos) {
        return TemplateEntity.create(
                templateId,
                sectionMapper.fromDTO(dtos)
        );
    }

}
