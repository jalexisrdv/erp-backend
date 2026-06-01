package com.jardvcode.erp.checklists.dto.template;

import java.util.List;

public record TemplateStructureRequestDTO(
        Long id,
        List<SectionDTO> sections
) {
}
