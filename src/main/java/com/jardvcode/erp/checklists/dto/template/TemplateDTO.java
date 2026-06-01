package com.jardvcode.erp.checklists.dto.template;

import com.jardvcode.erp.checklists.entity.template.TemplateEntity;

public record TemplateDTO(
        Long id,
        String name
) {

    public static TemplateDTO fromEntity(TemplateEntity entity) {
        return new TemplateDTO(
                entity.getId(),
                entity.getName()
        );
    }

}
