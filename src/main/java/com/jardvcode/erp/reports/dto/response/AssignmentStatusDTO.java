package com.jardvcode.erp.reports.dto.response;

import com.jardvcode.erp.reports.domain.AssignmentStatusEnum;
import com.jardvcode.erp.reports.entity.assignment.AssignmentEntity;

public record AssignmentStatusDTO(
        Long id,
        AssignmentStatusEnum status
) {

    public static AssignmentStatusDTO fromEntity(AssignmentEntity entity) {
        return new AssignmentStatusDTO(
                entity.getId(),
                entity.getStatus()
        );
    }

}
