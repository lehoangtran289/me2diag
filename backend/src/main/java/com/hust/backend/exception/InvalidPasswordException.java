package com.hust.backend.exception;

import com.hust.backend.exception.common.BusinessException;
import com.hust.backend.exception.common.ServiceError;

public class InvalidPasswordException extends BusinessException {
    public InvalidPasswordException() {
        super(ServiceError.PASSWORD_NOT_MATCH, null, null);
    }
}

