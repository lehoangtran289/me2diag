package com.hust.backend.locale;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class I18nMessageHelper {

    private static final String UNKNOW_ERROR = "Unknow error"; 
    private static final String UNKNOW_MESSAGE = "Unknow error"; 
    
    /** The message source. */
    private final MessageSource messageSource;


    public String getErrorMessage(String messageKey, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return getMessage(messageKey, args, locale, UNKNOW_ERROR);
    }
    
    public String getMessage(String messageKey, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return getMessage(messageKey, args, locale, UNKNOW_MESSAGE);
    }
    
    public String getMessage(String messageKey, Object[] args, Locale locale, String defaultMsg) {
        try {
            return messageSource.getMessage(messageKey, args, locale);
        } catch (Exception ex) {
            log.error("Can not find message", ex);
            return defaultMsg;
        }
    }
}
