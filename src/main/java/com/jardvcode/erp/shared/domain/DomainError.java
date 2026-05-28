package com.jardvcode.erp.shared.domain;

public class DomainError extends RuntimeException {

    private DomainErrorType errorType;
    private String action = "NONE";

    public DomainError(DomainErrorType errorType, String action, String message) {
        super(message);
        this.errorType = errorType;
        this.action = action;
    }

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

    public String action() {
        return action;
    }

}
