package com.hust.backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.auth")
@Getter
@Setter
public class AuthConfig {
    private boolean enable = true;
}
