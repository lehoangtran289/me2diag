package com.hust.backend.config.config;

import com.hust.backend.config.config.converter.TrimStringConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Trim all type of String
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new TrimStringConverter());
    }

    /**
     * By pass cors for web
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(
                        RequestMethod.GET.name(),
                        RequestMethod.POST.name(),
                        RequestMethod.PUT.name(),
                        RequestMethod.DELETE.name())
                .maxAge(1800L);
    }
}