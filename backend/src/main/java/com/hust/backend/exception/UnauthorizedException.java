package com.hust.backend.exception;

import com.hust.backend.exception.Common.BaseException;
import lombok.Getter;

@Getter
public class UnauthorizedException extends BaseException {
    private final String[] args;

    public UnauthorizedException(String message, String... args) {
        super(message);
        this.args = args;
    }
}
