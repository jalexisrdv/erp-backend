package com.jardvcode.erp.reports.mapper.assignment;

import com.jardvcode.erp.reports.dto.assignment.AssignmentDTO;
import com.jardvcode.erp.reports.dto.assignment.MechanicDTO;
import com.jardvcode.erp.reports.dto.assignment.OperatorDTO;
import com.jardvcode.erp.reports.dto.response.AssignmentStatusDTO;
import com.jardvcode.erp.reports.dto.response.ResponseRequestDTO;
import com.jardvcode.erp.reports.dto.template.TemplateDTO;
import com.jardvcode.erp.reports.entity.assignment.AssignmentEntity;
import com.jardvcode.erp.shared.mapper.AbstractMapper;

import java.util.List;

public final class AssignmentMapper extends AbstractMapper<AssignmentDTO, AssignmentEntity> {

    private final ResponseMapper responseMapper;

    public AssignmentMapper() {
        responseMapper = new ResponseMapper();
    }

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

    public AssignmentEntity toEntity(Long assignmentId, List<ResponseRequestDTO> dtos) {
        return AssignmentEntity.create(
                assignmentId,
                responseMapper.fromDTO(dtos)
        );
    }

    public AssignmentStatusDTO toDTO(AssignmentEntity entity) {
        return new AssignmentStatusDTO(
                entity.getId(),
                entity.getStatus()
        );
    }

}
