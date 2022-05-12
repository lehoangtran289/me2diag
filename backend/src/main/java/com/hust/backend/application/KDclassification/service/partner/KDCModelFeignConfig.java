package com.hust.backend.application.KDclassification.service.partner;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Configuration
public class KDCModelFeignConfig {
    @Bean
    public RequestInterceptor kdcModelFeignConfigRequestInterceptor() {
        return requestTemplate -> requestTemplate.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }
}
