package com.hust.backend.exception;

import com.hust.backend.exception.common.BusinessException;
import com.hust.backend.exception.common.ServiceError;

public class InternalException extends BusinessException {
    public InternalException(String msg) {
        super(ServiceError.UNEXPECTED_EXCEPTION, null, buildSingleParamMaps("err", msg));
    }
}
