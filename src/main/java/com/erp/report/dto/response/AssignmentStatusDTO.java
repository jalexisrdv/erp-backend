package com.erp.report.dto.response;

import com.erp.report.domain.AssignmentStatusEnum;

public record AssignmentStatusDTO(
        Long id,
        AssignmentStatusEnum status
) {
}
