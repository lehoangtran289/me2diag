package com.hust.backend.controller.restful.external;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@CacheConfig(cacheNames = "TestService")
public class TestService {

    @SneakyThrows
    public TestDTO get() {
        Thread.sleep(50);
        log.info("Get from method");
        return new TestDTO();
    }
}
