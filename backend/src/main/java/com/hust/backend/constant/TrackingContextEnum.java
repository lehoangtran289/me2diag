package com.hust.backend.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TrackingContextEnum {
    X_FORWARD_FOR("x-forwarded-for", "forwardIP"),
    X_REAL_IP("x-real-ip", "clientIP"),
    X_REQUEST_ID("x-request-id", "requestID"),
    X_CORRELATION_ID("X-Correlation-ID", "correlationID");

    private final String headerKey;
    private final String threadKey;
}
