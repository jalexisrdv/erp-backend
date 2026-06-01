package com.jardvcode.erp.reports.dto.template;

import com.jardvcode.erp.reports.entity.template.TemplateEntity;

import java.util.List;

public record TemplateCatalogDTO(
        Long id,
        String name
) {

    public static TemplateCatalogDTO fromEntity(TemplateEntity entity) {
        return new TemplateCatalogDTO(
                entity.getId(),
                entity.getName()
        );
    }

    public static List<TemplateCatalogDTO> fromEntities(List<TemplateEntity> entities) {
        return entities.stream()
                .map(TemplateCatalogDTO::fromEntity)
                .toList();
    }

}