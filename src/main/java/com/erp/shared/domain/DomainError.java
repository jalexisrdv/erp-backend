package com.erp.shared.domain;

public class DomainError extends RuntimeException {

    private DomainErrorType errorType;

    public DomainError(DomainErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public DomainError(String message) {
        this(DomainErrorType.DOMAIN, message);
    }

    public DomainErrorType errorType() {
        return errorType;
    }

}
