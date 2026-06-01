package com.jardvcode.erp.checklists.dto.response;

import com.jardvcode.erp.checklists.domain.AssignmentStatusEnum;
import com.jardvcode.erp.checklists.entity.assignment.AssignmentEntity;

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
