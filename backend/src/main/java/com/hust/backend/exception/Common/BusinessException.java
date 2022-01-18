package com.hust.backend.exception.Common;

import com.hust.backend.constant.ResponseStatusEnum;
import lombok.Getter;

@Getter
public class BusinessException extends BaseException {
    private final String[] args;
    private final ResponseStatusEnum responseStatusEnum;

    public BusinessException(ResponseStatusEnum responseStatusEnum, String... args) {
        super(responseStatusEnum.name());
        this.args = args;
        this.responseStatusEnum = responseStatusEnum;
    }
}
