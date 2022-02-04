package com.hust.backend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@Getter
public class ResetPasswordConfig {
    @Value("${client.context-path}")
    private String clientContextPath;
    @Value("${password-token-exp-time}")
    private Duration tokenExpireTime;
}

