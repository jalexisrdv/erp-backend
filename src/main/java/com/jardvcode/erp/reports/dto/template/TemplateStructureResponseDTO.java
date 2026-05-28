package com.jardvcode.erp.reports.dto.template;

import java.util.List;

public record TemplateStructureResponseDTO(
        Long id,
        String name,
        List<SectionDTO> sections
) {
}
