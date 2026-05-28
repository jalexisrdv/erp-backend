package com.jardvcode.erp.reports.dto.template;

import java.util.List;

public record TemplateStructureRequestDTO(
        Long id,
        List<SectionDTO> sections
) {
}
