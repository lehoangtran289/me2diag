package com.hust.backend.exception;

import com.hust.backend.exception.common.BusinessException;
import com.hust.backend.exception.common.ServiceError;

public class UserAlreadyExistException extends BusinessException {
    public UserAlreadyExistException() {
        super(ServiceError.USER_ALREADY_EXIST, null, null);
    }
}
