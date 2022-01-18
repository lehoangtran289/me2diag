package com.hust.backend.exception;

import com.hust.backend.exception.common.BusinessException;
import com.hust.backend.exception.common.ServiceError;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException() {
        super(ServiceError.ENTITY_NOT_FOUND, null, null);
    }
}
