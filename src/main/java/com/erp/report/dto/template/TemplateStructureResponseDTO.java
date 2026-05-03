package com.erp.report.dto.template;

import java.util.List;

public record TemplateStructureResponseDTO(
        Long id,
        String name,
        List<SectionDTO> sections
) {
}
