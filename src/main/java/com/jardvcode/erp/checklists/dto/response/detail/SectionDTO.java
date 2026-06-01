package com.jardvcode.erp.checklists.dto.response.detail;

import java.util.List;

public record SectionDTO(
        Long id,
        String name,
        List<ResponseDTO> responses,
        Integer position
) {
}
