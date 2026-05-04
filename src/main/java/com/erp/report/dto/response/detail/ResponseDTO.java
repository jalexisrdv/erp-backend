package com.erp.report.dto.response.detail;

import com.erp.report.domain.ResponseStatusEnum;

public record ResponseDTO(
        Long id,
        String label,
        ResponseStatusEnum status,
        String comment,
        Integer position
) {
}
