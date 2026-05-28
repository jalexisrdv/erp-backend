package com.jardvcode.erp.reports.exception.template.section;

import com.jardvcode.erp.shared.domain.DomainError;

public final class EmptySectionsException extends DomainError {
    public EmptySectionsException() {
        super("No se enviaron secciones para procesar.");
    }
}
