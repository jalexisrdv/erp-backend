package com.erp.report.dto.response.detail;

import java.util.List;

public record SectionDTO(
        Long id,
        String name,
        List<ResponseDTO> responses
) {
}
