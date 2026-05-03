package com.erp.report.mapper.template;

import com.erp.report.dto.template.SectionDTO;
import com.erp.report.dto.template.TemplateDTO;
import com.erp.report.entity.template.SectionEntity;
import com.erp.report.entity.template.TemplateEntity;
import com.erp.shared.mapper.AbstractMapper;

import java.util.List;
import java.util.stream.Collectors;

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
