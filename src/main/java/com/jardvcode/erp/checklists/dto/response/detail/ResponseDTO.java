package com.jardvcode.erp.checklists.dto.response.detail;

import com.jardvcode.erp.checklists.domain.ResponseStatusEnum;
import com.jardvcode.erp.checklists.entity.assignment.AssignmentItemEntity;
import com.jardvcode.erp.checklists.entity.assignment.AssignmentResponseEntity;

public record ResponseDTO(
        Long id,
        String label,
        ResponseStatusEnum status,
        String comment,
        Integer position
) {

    public static ResponseDTO fromEntity(AssignmentItemEntity item) {
        AssignmentResponseEntity response = item.getResponse();

        return new ResponseDTO(
                response.getId(),
                item.getLabel(),
                response.getStatus(),
                response.getComment(),
                item.getPosition()
        );
    }

}
