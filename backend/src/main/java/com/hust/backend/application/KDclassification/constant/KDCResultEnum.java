package com.hust.backend.application.KDclassification.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.hust.backend.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum KDCResultEnum {
    N00("0"),
    N04("1"),
    N17("2"),
    N18("3");

    private final String value;

    private static final Map<String, KDCResultEnum> map = new HashMap<>();
    static {
        for (KDCResultEnum e : values()) {
            map.put(e.getValue(), e);
        }
    }

    @JsonCreator
    public static KDCResultEnum from (String e) {
        return Optional.ofNullable(map.get(e))
                .orElseThrow(() -> new NotFoundException(KDCResultEnum.class, e));
    }

    @Override
    public String toString() {
        return value;
    }
}