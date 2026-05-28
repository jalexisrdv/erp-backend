package com.jardvcode.erp.reports.dto.response.detail;

import com.jardvcode.erp.reports.domain.ResponseStatusEnum;

public record ResponseDTO(
        Long id,
        String label,
        ResponseStatusEnum status,
        String comment,
        Integer position
) {
}
