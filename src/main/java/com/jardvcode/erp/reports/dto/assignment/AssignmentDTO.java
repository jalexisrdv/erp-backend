package com.jardvcode.erp.reports.dto.assignment;

import com.jardvcode.erp.reports.domain.AssignmentStatusEnum;
import com.jardvcode.erp.reports.dto.template.TemplateDTO;

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
}
