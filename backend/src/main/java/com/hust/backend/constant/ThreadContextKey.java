package com.hust.backend.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadContextKey {
    public static final String TRACE_ID = "traceId";
}
