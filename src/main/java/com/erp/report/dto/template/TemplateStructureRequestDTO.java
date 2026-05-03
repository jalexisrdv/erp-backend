package com.erp.report.dto.template;

import java.util.List;

public record TemplateStructureRequestDTO(
        Long id,
        List<SectionDTO> sections
) {
}
