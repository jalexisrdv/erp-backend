package com.jardvcode.erp.checklists.dto.response.detail;

import com.jardvcode.erp.checklists.domain.ResponseStatusEnum;

public record ResponseDTO(
        Long id,
        String label,
        ResponseStatusEnum status,
        String comment,
        Integer position
) {
}
