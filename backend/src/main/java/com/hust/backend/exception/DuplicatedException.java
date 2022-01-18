package com.hust.backend.exception;

import com.hust.backend.exception.Common.BaseException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@SuppressWarnings("java:S1165")
public class DuplicatedException extends BaseException {
    private static final String MESSAGE_FORMAT = "Data is duplicated at Entity name: %s with identifier: %s";
    private Class<?> target;
    private String identifier;

    public DuplicatedException(Class<?> target, String identifier) {
        super(String.format(MESSAGE_FORMAT, target.getName(), identifier));
        this.target = target;
        this.identifier = identifier;
        log.info(super.getMessage());
    }

    public DuplicatedException(String message) {
        super(message);
    }
}
