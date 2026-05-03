package com.erp.report.dto.template;

public record ItemDTO(
        Long id,
        String uuid,
        String sectionUuid,
        String label,
        Integer position
) {
}
