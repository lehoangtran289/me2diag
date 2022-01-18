package com.hust.backend.exception;

import com.hust.backend.exception.Common.BaseException;
import com.hust.backend.utils.Common;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@SuppressWarnings("java:S1165")
public class NotValidException extends BaseException {
    private static final String MESSAGE_FORMAT = "Data is not valid %s.";
    private final String data;
    private Class<?> target;

    public <T> NotValidException(Class<?> target, T data) {
        super(String.format(MESSAGE_FORMAT, target.getName()));
        this.target = target;
        this.data = Common.toJson(data);
        log.info(super.getMessage() + " Request Data: " + this.data);
    }

    public NotValidException(String message) {
        super(message);
        this.data = message;
        log.info(super.getMessage() + " Request Data: " + this.data);
    }
}
