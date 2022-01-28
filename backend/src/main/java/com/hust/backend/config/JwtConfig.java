package com.hust.backend.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfig {

    @Value("${jwt.access-token.seconds-expired}")
    private Duration accessTokenExpTime;

    @Value("${jwt.refresh-token.seconds-expired}")
    private Duration refreshTokenExpTime;

    @Value("${jwt.expiration-threshold:10}")
    private Duration expThreshold;

    @Value("${jwt.secret}")
    private String jwtSecret;
}
