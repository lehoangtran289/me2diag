package com.hust.backend.exception.common;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public abstract class BaseException extends RuntimeException {

    private final ServiceError err;
    private final Map<String, Object> params;
    
    private static final long serialVersionUID = -6662460118306199774L;
    
    protected BaseException(ServiceError err, Throwable ex, LinkedHashMap<String, Object> params) {
        super(err.getMessageKey(), ex);
        this.params = Objects.nonNull(params) ? params: Collections.emptyMap();
        this.err = err;
    }

    public ServiceError getErr() {
        return err;
    }

    public Map<String, Object> getParams() {
        return params;
    }
}