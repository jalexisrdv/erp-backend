package com.jardvcode.erp.checklists.dto.response.detail;

public record HeaderDTO(
        String unitNumber,
        String templateName,
        String operatorName,
        String mechanic,
        String mileage,
        String nextService,
        String timeIn,
        String timeOut,
        String date
) {

}
