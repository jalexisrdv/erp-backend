package com.erp.report.mapper.assignment;

import com.erp.report.dto.response.detail.HeaderDTO;
import com.erp.report.dto.response.detail.ReportDTO;
import com.erp.report.dto.response.detail.ResponseDTO;
import com.erp.report.dto.response.detail.SectionDTO;
import com.erp.report.entity.assignment.AssignmentEntity;
import com.erp.report.entity.template.SectionEntity;

import java.util.ArrayList;
import java.util.HashMap;

public final class ReportPreviewMapper {

    public ReportDTO fromEntity(AssignmentEntity assignment) {
        HashMap<String, SectionDTO> sections = new HashMap<>();

        assignment.getResponses().forEach(responseEntity -> {
            SectionEntity sectionEntity = responseEntity.section();

            SectionDTO section = sections.computeIfAbsent(
                    sectionEntity.getName(),
                    key -> new SectionDTO(
                            sectionEntity.getId(),
                            sectionEntity.getName(),
                            new ArrayList<>()
                    )
            );

            ResponseDTO response = new ResponseDTO(
                    responseEntity.getId(),
                    responseEntity.getItem().getLabel(),
                    responseEntity.getStatus(),
                    responseEntity.getComment()
            );

            section.responses().add(response);
        });

        HeaderDTO header = new HeaderDTO(
                assignment.getUnitNumber().toString(),
                assignment.getTemplate().getName(),
                assignment.getOperator().fullName(),
                assignment.getMechanic().fullName(),
                assignment.getMileage(),
                assignment.getNextService(),
                assignment.getTimeIn().toString(),
                assignment.getTimeOut().toString(),
                assignment.getDate().toString()
        );

        return new ReportDTO(
                header,
                new ArrayList<>(sections.values())
        );
    }

}
