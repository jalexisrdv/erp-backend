package com.erp.report.dto.template.preview;

import java.util.List;

public record SectionDTO(
        Long id,
        String name,
        List<ItemDTO> items
) {
}
