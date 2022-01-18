package com.hust.backend.locale;

import com.hust.backend.config.AppConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
public class ClientLocaleResolver extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {
    final AppConfig appConfig;
    private final List<Locale> locales = new ArrayList<>();
    private final Locale defaultLanguage;

    public ClientLocaleResolver(AppConfig appConfig) {
        this.appConfig = appConfig;
        this.appConfig.getLocaleResolverLanguages().forEach(s -> locales.add(new Locale(s)));
        this.defaultLanguage = new Locale(this.appConfig.getDefaultLanguage());
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader("Accept-Language");
        return headerLang == null || headerLang.isEmpty()
                ? this.defaultLanguage
                : Locale.lookup(Locale.LanguageRange.parse(headerLang), locales);
    }

    @Bean
    @Primary
    public LocaleResolver localeResolver() {
        return this;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename("messages");
        rs.setDefaultEncoding("UTF-8");
        rs.setUseCodeAsDefaultMessage(false);
        return rs;
    }
}
