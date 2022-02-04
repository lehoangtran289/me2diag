package com.hust.backend.exception;

import com.hust.backend.exception.Common.BaseException;
import lombok.Getter;

@Getter
public class RefreshTokenException extends BaseException {
    private final String[] args;

    public RefreshTokenException(String message, String... args) {
        super(message);
        this.args = args;
    }
}
