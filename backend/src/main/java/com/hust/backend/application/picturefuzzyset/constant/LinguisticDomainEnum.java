package com.hust.backend.application.picturefuzzyset.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.hust.backend.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum LinguisticDomainEnum {
    NONE("NONE"),
    VERY_LOW("VERY LOW"),
    LOW("LOW"),
    SLIGHTLY_LOW("SLIGHTLY LOW"),
    MEDIUM("MEDIUM"),
    SLIGHTLY_HIGH("SLIGHTLY HIGH"),
    HIGH("HIGH"),
    VERY_HIGH("VERY HIGH"),
    COMPLETELY("COMPLETELY");

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
