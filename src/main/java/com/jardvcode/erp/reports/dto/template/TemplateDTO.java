package com.jardvcode.erp.reports.dto.template;

import com.jardvcode.erp.reports.entity.template.TemplateEntity;

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
