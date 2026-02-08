package com.erp.report.mapper.assignment;

import com.erp.report.dto.assignment.AssignmentDTO;
import com.erp.report.dto.assignment.MechanicDTO;
import com.erp.report.dto.assignment.OperatorDTO;
import com.erp.report.dto.template.TemplateDTO;
import com.erp.report.entity.assignment.AssignmentEntity;
import com.erp.shared.mapper.AbstractMapper;

public final class AssignmentMapper extends AbstractMapper<AssignmentDTO, AssignmentEntity> {
    @Override
    public AssignmentEntity fromDTO(AssignmentDTO dto) {
        return AssignmentEntity.create(
                dto.id(),
                dto.template().id(),
                dto.unitNumber(),
                dto.operator().id(),
                dto.mechanic().id(),
                dto.mileage(),
                dto.nextService(),
                dto.timeIn(),
                dto.timeOut()
        );
    }

    @Override
    public AssignmentDTO fromEntity(AssignmentEntity entity) {
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
