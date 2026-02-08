package com.erp.report.mapper.assignment;

import com.erp.report.dto.response.detail.HeaderDTO;
import com.erp.report.dto.response.detail.ReportDTO;
import com.erp.report.dto.response.detail.ResponseDTO;
import com.erp.report.dto.response.detail.SectionDTO;
import com.erp.report.entity.assignment.AssignmentEntity;
import com.erp.report.entity.assignment.ResponseEntity;
import com.erp.report.entity.template.SectionEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class ReportMapper {
    public ReportDTO fromEntity(List<ResponseEntity> entities) {
        HashMap<String, SectionDTO> responses = new HashMap<>();

        entities.forEach(entity -> {
            SectionEntity sectionEntity = entity.section();

            if(responses.containsKey(sectionEntity.getName())) {
                ResponseDTO response = new ResponseDTO(
                        entity.getId(),
                        entity.getItem().getLabel(),
                        entity.getStatus(),
                        entity.getComment()
                );

                responses.get(sectionEntity.getName()).responses().add(response);
            } else {
                SectionDTO section = new SectionDTO(
                        sectionEntity.getId(),
                        sectionEntity.getName(),
                        new ArrayList<>()
                );

                responses.put(sectionEntity.getName(), section);
            }
        });

        if(responses.isEmpty()) {
            return null;
        }

        AssignmentEntity assignmentEntity = entities.get(0).getAssignment();

        HeaderDTO headerDTO = new HeaderDTO(
                assignmentEntity.getUnitNumber().toString(),
                assignmentEntity.getTemplate().getName(),
                assignmentEntity.getOperator().fullName(),
                assignmentEntity.getMechanic().fullName(),
                assignmentEntity.getMileage(),
                assignmentEntity.getNextService(),
                assignmentEntity.getTimeIn().toString(),
                assignmentEntity.getTimeOut().toString(),
                assignmentEntity.getDate().toString()
        );

        ReportDTO report = new ReportDTO(
                headerDTO,
                responses.values().stream().toList()
        );

        return report;
    }
}
