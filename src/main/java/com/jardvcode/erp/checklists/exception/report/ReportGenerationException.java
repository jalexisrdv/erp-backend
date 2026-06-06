package com.jardvcode.erp.checklists.exception.report;

import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;

public final class ReportGenerationException extends DomainError {
    private static final String message = "No se pudo generar el reporte.";

    public ReportGenerationException(DomainErrorType domainErrorType) {
        super(message);
    }

    public ReportGenerationException() {
        super(message);
    }
}
