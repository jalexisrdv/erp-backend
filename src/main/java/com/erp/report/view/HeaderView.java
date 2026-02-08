package com.erp.report.view;

public record HeaderView(
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
