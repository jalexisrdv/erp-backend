package com.jardvcode.erp.checklists.dto.response.detail;

import com.jardvcode.erp.checklists.entity.assignment.AssignmentEntity;
import com.jardvcode.erp.checklists.entity.assignment.AssignmentSectionEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public record ReportDTO(
        HeaderDTO header,
        List<SectionDTO> sections
) {

    public static ReportDTO fromEntity(AssignmentEntity assignment) {
        HeaderDTO header = new HeaderDTO(
                assignment.getUnitNumber().toString(),
                assignment.getTemplateName(),
                assignment.getOperatorFullName(),
                assignment.getMechanicFullName(),
                assignment.getMileage(),
                assignment.getNextService(),
                assignment.getTimeIn().toString(),
                assignment.getTimeOut().toString(),
                assignment.getDate().toString()
        );

        List<SectionDTO> sections = assignment.getSections().stream()
                .map(SectionDTO::fromEntity)
                .collect(Collectors.toList());

        return new ReportDTO(
                header,
                new ArrayList<>(sections)
        );
    }

}