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
public enum LinguisticDomainEnum {
    // PFS
    NONE("NONE"),
    VERY_LOW("VERY LOW"),
    LOW("LOW"),
    SLIGHTLY_LOW("SLIGHTLY LOW"),
    MEDIUM("MEDIUM"),
    SLIGHTLY_HIGH("SLIGHTLY HIGH"),
    HIGH("HIGH"),
    VERY_HIGH("VERY HIGH"),
    COMPLETELY("COMPLETELY"),

    // KDC
    // VERY_LOW
    MORE_LOW("COMPLETELY"),
    //LOW
    POSSIBLE_LOW("COMPLETELY"),
    LITTLE_LOW("COMPLETELY"),
    // MEDIUM
    LITTLE_HIGH("COMPLETELY"),
    POSSIBLE_HIGH("COMPLETELY"),
    // HIGH
    MORE_HIGH("COMPLETELY");
    // VERY_HIGH

    private final String value;

    private static final Map<String, LinguisticDomainEnum> map = new HashMap<>();
    static {
        for (LinguisticDomainEnum linguisticDomainEnum : values()) {
            map.put(linguisticDomainEnum.getValue(), linguisticDomainEnum);
        }
    }

    @JsonCreator
    public static LinguisticDomainEnum from (String linguisticElement) {
        return Optional.ofNullable(map.get(linguisticElement))
                .orElseThrow(() -> new NotFoundException(SymptomEnum.class, linguisticElement));
    }

    @Override
    public String toString() {
        return value;
    }
}
