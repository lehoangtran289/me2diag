package com.hust.backend.exception;

import com.hust.backend.exception.common.BusinessException;
import com.hust.backend.exception.common.ServiceError;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String userName) {
        super(ServiceError.USER_NOT_FOUND, null, buildSingleParamMaps("userName", userName));
    }
}
