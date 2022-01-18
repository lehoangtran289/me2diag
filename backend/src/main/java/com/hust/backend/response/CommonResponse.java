package com.hust.backend.response;

import lombok.Getter;

@Getter
public class CommonResponse<T> extends Response {
    private final T data;

    protected CommonResponse(String message, T data) {
        this.status = Status.OK;
        this.message = message;
        this.data = data;
    }

    public static <P> CommonResponse<P> of(String message, P data) {
        return new CommonResponse<>(message, data);
    }
}
