package com.hust.backend.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.hust.backend.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@AllArgsConstructor
@Getter
public enum UserGenderEnum {
    MALE("male"),
    FEMALE("female");

    private final String gender;

    private static final Map<String, UserGenderEnum> map = new HashMap<>();
    static {
        for (UserGenderEnum genderEnum : values()) {
            map.put(genderEnum.getGender(), genderEnum);
        }
    }

    @JsonCreator
    public static UserGenderEnum from (String gender) {
        return Optional.ofNullable(map.get(gender))
                .orElseThrow(() -> new NotFoundException(UserGenderEnum.class, gender));
    }

    @Override
    public String toString() {
        return gender;
    }
}
