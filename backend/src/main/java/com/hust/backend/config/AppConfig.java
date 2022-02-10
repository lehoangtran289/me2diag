package com.hust.backend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@Getter
public class AppConfig {
    @Value("#{'${spring.application.name}'.toUpperCase()}")
    private String appName;
    @Value("#{'${app.environment}'.toUpperCase()}")
    private String env;
    @Value("${spring.application.domain}")
    private String domain;
    private final List<String> localeResolverLanguages = Arrays.asList("en", "vi");
    private final String defaultLanguage = "en";
}
