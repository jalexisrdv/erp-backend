package com.erp.report.dto.response;

import com.erp.report.domain.ResponseStatusEnum;

public record ResponseRequestDTO(
        Long id,
        ResponseStatusEnum status,
        String comment
) {
}
