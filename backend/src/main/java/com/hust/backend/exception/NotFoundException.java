package com.hust.backend.exception;

import com.hust.backend.exception.Common.BaseException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@SuppressWarnings("java:S1165")
public class NotFoundException extends BaseException {
    private static final String MESSAGE_FORMAT = "Not found Entity name: %s with identifier: %s";
    private Class<?> target;
    private String identifier;

    public NotFoundException(Class<?> target, String identifier) {
        super(String.format(MESSAGE_FORMAT, target.getName(), identifier));
        this.target = target;
        this.identifier = identifier;
        log.info(super.getMessage());
    }

    public NotFoundException(String message) {
        super(message);
    }
}
