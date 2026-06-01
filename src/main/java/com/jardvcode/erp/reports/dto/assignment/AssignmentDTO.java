package com.jardvcode.erp.reports.dto.assignment;

import com.jardvcode.erp.reports.domain.AssignmentStatusEnum;
import com.jardvcode.erp.reports.dto.template.TemplateDTO;
import com.jardvcode.erp.reports.entity.assignment.AssignmentEntity;

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
                        entity.getTemplate().getId(),
                        entity.getTemplate().getName()
                ),
                entity.getUnitNumber(),
                new OperatorDTO(
                        entity.getOperator().getId(),
                        entity.getOperator().fullName()
                ),
                new MechanicDTO(
                        entity.getMechanic().getId(),
                        entity.getMechanic().fullName()
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
