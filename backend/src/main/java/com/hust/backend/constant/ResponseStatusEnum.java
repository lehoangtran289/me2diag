package com.hust.backend.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseStatusEnum {
    SUCCESS(0, HttpStatus.OK.value()),
    INTERNAL_SERVER_ERROR(9999, HttpStatus.INTERNAL_SERVER_ERROR.value()),
    BAD_REQUEST(9998, HttpStatus.BAD_REQUEST.value()),
    DATA_NOT_VALID(9997, HttpStatus.BAD_REQUEST.value()),
    ENTITY_DUPLICATED(9996, HttpStatus.CONFLICT.value()),
    ENTITY_NOT_FOUND(9995, HttpStatus.NOT_FOUND.value()),
    FORBIDDEN(9993, HttpStatus.FORBIDDEN.value()),
    UNAUTHORIZED(99992, HttpStatus.UNAUTHORIZED.value())
    ;

    private final int code;
    private final int httpCode;

    ResponseStatusEnum(int code, int httpCode) {
        this.code = code;
        this.httpCode = httpCode;
    }
}
