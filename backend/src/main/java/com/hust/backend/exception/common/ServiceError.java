package com.hust.backend.exception.common;

import lombok.Getter;

@Getter
public enum ServiceError {
    PASSWORD_NOT_MATCH(401, "err.authorize.password-not-match"),
    USER_NOT_FOUND(401, "err.api.user-not-found"),

    USER_ALREADY_EXIST(400, "err.api.user-already-exist"),

    INVALID_TOKEN(401, "err.authorize.invalid-token"),
    INVALID_TOKEN_FORMAT(401, "err.authorize.invalid-token-format"),
    TOKEN_INFO_INCORRECT(401, "err.authorize.token-info-incorrect"),
    SESSION_EXPIRED(401, "err.authorize.session-expired"),
    ACCESS_DENIED(401, "err.authorize.access-denied"),
    INVALID_PARAM(400, "err.api.invalid-param"),
    HTTP_METHOD_NOT_SUPPORT(405, "err.api.http-method-not-support"),
    MEDIA_TYPE_NOT_SUPPORT(415, "err.api.media-type-not-support"),
    MESSAGE_NOT_READABLE(400, "err.api.message-not-readable"),
    ENTITY_NOT_FOUND(404, "err.api.entity-not-found"),
    UNEXPECTED_EXCEPTION(500, "err.sys.unexpected-exception");

    ServiceError(int errCode, String messageKey) {
        this.errCode = errCode;
        this.messageKey = messageKey;
    }

    private final int errCode;
    private final String messageKey;
}
