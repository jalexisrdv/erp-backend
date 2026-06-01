package com.jardvcode.erp.checklists.dto.template;

import com.jardvcode.erp.checklists.entity.template.TemplateEntity;

import java.util.List;

public record TemplateStructureResponseDTO(
        Long id,
        String name,
        List<SectionDTO> sections
) {

    public static TemplateStructureResponseDTO fromEntity(TemplateEntity entity) {
        return new TemplateStructureResponseDTO(
                entity.getId(),
                entity.getName(),
                SectionDTO.fromEntities(entity.getSections())
        );
    }

}
