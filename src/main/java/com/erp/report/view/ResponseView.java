package com.erp.report.view;

public record ResponseView(
        Long id,
        String label,
        String status,
        String comment
) {
}
