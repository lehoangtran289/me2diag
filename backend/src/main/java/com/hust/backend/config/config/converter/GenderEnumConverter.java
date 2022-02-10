package com.hust.backend.config.config.converter;

import com.hust.backend.constant.UserGenderEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class GenderEnumConverter implements Converter<String, UserGenderEnum> {

    @Override
    public UserGenderEnum convert(@NonNull String source) {
        return UserGenderEnum.from(source.toLowerCase());
    }
}
