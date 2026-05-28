package com.jardvcode.erp.reports.dto.template;

public record ItemDTO(
        Long id,
        String uuid,
        String sectionUuid,
        String label,
        Integer position
) {
}
