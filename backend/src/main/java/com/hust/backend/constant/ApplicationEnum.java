package com.hust.backend.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.hust.backend.application.picturefuzzyset.constant.SymptomEnum;
import com.hust.backend.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum ApplicationEnum {
    PFS("PFS"),
    KDC("KDC");

    private final String value;

    private static final Map<String, ApplicationEnum> map = new HashMap<>();
    static {
        for (ApplicationEnum appEnum : values()) {
            map.put(appEnum.getValue(), appEnum);
        }
    }

    @JsonCreator
    public static ApplicationEnum from (String key) {
        return Optional.ofNullable(map.get(key))
                .orElseThrow(() -> new NotFoundException(SymptomEnum.class, key));
    }

    @Override
    public String toString() {
        return value;
    }
}

