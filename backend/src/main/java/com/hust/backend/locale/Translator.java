package com.hust.backend.locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translator {
    private static final String SYSTEM_ERROR_CODE = "9999";
    private final ResourceBundleMessageSource messageSource;

    public Translator(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getLocaleMessage(String code) {
        String res = getMessage(code, messageSource);
        return StringUtils.isBlank(res) ? getMessage(SYSTEM_ERROR_CODE, messageSource) : res;
    }

    private String getMessage(String code, ResourceBundleMessageSource messageSource) {
        try {
            Locale locale = LocaleContextHolder.getLocale();
            return messageSource.getMessage(code, null, locale);
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

}
