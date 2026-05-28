package com.jardvcode.erp.reports.dto.response;

import com.jardvcode.erp.reports.domain.ResponseStatusEnum;

public record ResponseRequestDTO(
        Long id,
        ResponseStatusEnum status,
        String comment
) {
}
