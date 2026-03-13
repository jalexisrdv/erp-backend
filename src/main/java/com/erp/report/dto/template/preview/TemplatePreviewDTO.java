package com.erp.report.dto.template.preview;

import com.erp.report.dto.response.detail.HeaderDTO;

import java.util.List;

public record TemplatePreviewDTO(
        HeaderDTO header,
        List<SectionDTO> sections
) {
}
