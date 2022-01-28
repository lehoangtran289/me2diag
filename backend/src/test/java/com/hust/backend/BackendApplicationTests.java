package com.hust.backend;

import com.hust.backend.entity.UserEntity;
import com.hust.backend.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private JwtService jwtService;

    @Test
    public void generateToken() {
        System.out.println(jwtService.validateToken(jwtService.generateAccessToken(
                UserEntity.builder().id("123").build()
        )));
    }

}
