package com.hust.backend.config.config.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class TrimStringConverter implements Converter<String, String> {

    @Override
    public String convert(String source) {
        return StringUtils.trim(source);
    }
}
