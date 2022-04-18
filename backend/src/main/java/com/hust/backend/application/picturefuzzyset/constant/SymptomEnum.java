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
public enum SymptomEnum {
    TEMPERATURE("TEMPERATURE"),
    HEADACHE("HEADACHE"),
    STOMACH_PAIN("STOMACH_PAIN"),
    COUGH("COUGH"),
    CHEST_PAIN("CHEST_PAIN");

    private final String symptom;

    private static final Map<String, SymptomEnum> map = new HashMap<>();
    static {
        for (SymptomEnum symptomEnum : values()) {
            map.put(symptomEnum.getSymptom(), symptomEnum);
        }
    }

    @JsonCreator
    public static SymptomEnum from (String symptom) {
        return Optional.ofNullable(map.get(symptom))
                .orElseThrow(() -> new NotFoundException(SymptomEnum.class, symptom));
    }

    @Override
    public String toString() {
        return symptom;
    }
}
