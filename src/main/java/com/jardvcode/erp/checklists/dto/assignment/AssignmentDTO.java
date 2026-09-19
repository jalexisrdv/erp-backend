package com.jardvcode.erp.checklists.dto.assignment;

import com.jardvcode.erp.checklists.domain.AssignmentStatusEnum;
import com.jardvcode.erp.checklists.dto.template.TemplateDTO;
import com.jardvcode.erp.checklists.entity.assignment.AssignmentEntity;

public record AssignmentDTO(
        Long id,
        TemplateDTO template,
        Integer unitNumber,
        OperatorDTO operator,
        MechanicDTO mechanic,
        String mileage,
        String nextService,
        String timeIn,
        String timeOut,
        String date,
        AssignmentStatusEnum status
) {

    public static AssignmentDTO fromEntity(AssignmentEntity entity) {
        return new AssignmentDTO(
                entity.getId(),
                new TemplateDTO(
                        entity.getTemplateId(),
                        entity.getTemplateName()
                ),
                entity.getUnitNumber(),
                new OperatorDTO(
                        entity.getOperatorUserId(),
                        entity.getOperatorFullName()
                ),
                new MechanicDTO(
                        entity.getMechanicUserId(),
                        entity.getMechanicFullName()
                ),
                entity.getMileage(),
                entity.getNextService(),
                entity.getTimeIn().toString(),
                entity.getTimeOut().toString(),
                entity.getDate().toString(),
                entity.getStatus()
        );
    }

}
