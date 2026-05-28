package com.jardvcode.erp.reports.dto.response;

import com.jardvcode.erp.reports.domain.AssignmentStatusEnum;

public record AssignmentStatusDTO(
        Long id,
        AssignmentStatusEnum status
) {
}
