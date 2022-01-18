package com.hust.backend.locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

/**
 * Configure for internationalization
 *
 */
@Configuration
public class LocaleConfiguration implements WebMvcConfigurer {

    private static final String MESSAGE_SOURCE_BASE = "classpath:i18n/messages";
    private static final String ERR_MESSAGE_SOURCE_BASE = "classpath:i18n/errmessages";
    private static final List<Locale> LOCALES = List.of(
            new Locale("en"),
            new Locale("vi"));

    /***
     * determine the current locale based on the session, cookies, the
     * Accept-Language header, or a fixed value.
     * 
     * @return localeResolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver slr = new AcceptHeaderLocaleResolver();
        slr.setSupportedLocales(LOCALES);
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

//    /**
//     * Interceptor to change locate base on lang parameter
//     * 
//     * @return
//     */
//    @Bean
//    public LocaleChangeInterceptor localeChangeInterceptor() {
//        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
//        lci.setParamName("lang");
//        return lci;
//    }

//    /***
//     * add interceptor
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(localeChangeInterceptor());
//    }

    /***
     * Configure for message source
     * 
     * @return messageSource
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(MESSAGE_SOURCE_BASE, ERR_MESSAGE_SOURCE_BASE);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setCacheSeconds(8 * 60 * 60); // reload messages every 1 hours
        return messageSource;
    }
}
