package com.hust.backend.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.hust.backend.application.picturefuzzyset.constant.SymptomEnum;
import com.hust.backend.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum UserRoleEnum {
    USER("USER"), ADMIN("ADMIN"), EXPERT("EXPERT");

    private final String role;

    private static final Map<String, UserRoleEnum> map = new HashMap<>();

    static {
        for (UserRoleEnum e : values()) {
            map.put(e.getRole(), e);
        }
    }

    public static UserRoleEnum from(String role) {
        return Optional.ofNullable(map.get(role))
                .orElseThrow(() -> new NotFoundException(UserRoleEnum.class, role));
    }

    @Override
    public String toString() {
        return role;
    }
}
