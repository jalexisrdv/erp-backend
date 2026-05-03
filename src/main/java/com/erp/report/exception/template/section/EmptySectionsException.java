package com.erp.report.exception.template.section;

import com.erp.shared.domain.DomainError;

public final class EmptySectionsException extends DomainError {
    public EmptySectionsException() {
        super("No se enviaron secciones para procesar.");
    }
}
