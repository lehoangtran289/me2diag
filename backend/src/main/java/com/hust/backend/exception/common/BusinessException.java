package com.hust.backend.exception.common;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * thing need return message to client
 * @author Admin
 *
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = 6910434576449212427L;

    protected BusinessException(ServiceError err, Throwable ex, LinkedHashMap<String, Object> params) {
        super(err, ex, params);
    }

    protected static LinkedHashMap<String, Object> buildSingleParamMaps(String key, String value) {
        return Maps.newLinkedHashMap(Collections.singletonMap(key, value));
    }
}
