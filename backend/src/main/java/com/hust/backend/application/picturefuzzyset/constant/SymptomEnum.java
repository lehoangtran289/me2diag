package com.hust.backend.application.picturefuzzyset.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.hust.backend.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The order of elements in this enum MUST BE SAME to those in any request body of the api /pfs/diagnose
 * {
 * 	"patient_id": "0123",
 * 	"symptoms": [
 *                {
 *             "TEMPERATURE" : {
 * 			    "positive": 0.7,
 * 			    "neutral": "VERY LOW",
 * 			    "negative": 0.1
 *            }
 *         },
 *        {
 *             "HEADACHE" : {
 * 			    "positive": 0.7,
 * 			    "neutral": 0.05,
 * 			    "negative": 0.2
 *            }
 *         },
 *         {
 *             "STOMACH_PAIN" : {
 * 			    "positive": 0.1,
 * 			    "neutral": 0.2,
 * 			    "negative": 0.6
 *            }
 *         },
 *        {
 *             "COUGH" : {
 * 			    "positive": 0.7,
 * 			    "neutral": 0.15,
 * 			    "negative": 0.1
 *            }
 *         },
 *         {
 *             "CHEST_PAIN" : {
 * 			    "positive": 0.2,
 * 			    "neutral": 0.3,
 * 			    "negative": 0.5
 *            }
 *         }
 * 	]
 * }
 */
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
