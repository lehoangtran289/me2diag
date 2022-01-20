package com.hust.backend.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseStatusEnum {
    SUCCESS("00", HttpStatus.OK.value()),
    CREATED("00", HttpStatus.CREATED.value()),

    INTERNAL_SERVER_ERROR("ER99", HttpStatus.INTERNAL_SERVER_ERROR.value()),
    BAD_REQUEST("ER98", HttpStatus.BAD_REQUEST.value()),
    DATA_NOT_VALID("ER97", HttpStatus.BAD_REQUEST.value()),
    ENTITY_DUPLICATED("ER96", HttpStatus.CONFLICT.value()),
    ENTITY_NOT_FOUND("ER95", HttpStatus.NOT_FOUND.value()),
    FORBIDDEN("ER93", HttpStatus.FORBIDDEN.value()),
    UNAUTHORIZED("ER92", HttpStatus.UNAUTHORIZED.value()),
    METHOD_NOT_SUPPORT("ER91", HttpStatus.HTTP_VERSION_NOT_SUPPORTED.value()),
    UNSUPPORTED_MEDIA_TYPE("ER90", HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
    ;

    private final String code;
    private final int httpCode;

    ResponseStatusEnum(String code, int httpCode) {
        this.code = code;
        this.httpCode = httpCode;
    }
}
