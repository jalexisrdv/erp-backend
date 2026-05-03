package com.erp.report.dto.template;

import java.util.List;

public record SectionDTO(
        Long id,
        String uuid,
        Long templateId,
        String name,
        List<ItemDTO> items,
        Integer position
) {
}
