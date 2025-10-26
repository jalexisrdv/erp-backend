package com.erp.shared.domain;

import org.springframework.http.HttpStatus;

public final class HttpCode {

    public static Integer badRequest() {
        return HttpStatus.BAD_REQUEST.value();
    }

    public static Integer conflict() {
        return HttpStatus.CONFLICT.value();
    }

}
