package com.erp.shared.domain;

public class DomainError extends RuntimeException {

    private Integer httpCode;

    public DomainError(Integer httpCode, String message) {
        super(message);
        this.httpCode = httpCode;
    }

    public Integer getHttpCode() {
        return httpCode;
    }
}
