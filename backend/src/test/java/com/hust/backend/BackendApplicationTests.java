package com.hust.backend;

import com.hust.backend.entity.UserEntity;
import com.hust.backend.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private AuthService jwtService;

    @Test
    public void generateToken() {
//        System.out.println(jwtService.validateToken(jwtService.generateAccessToken(
//                UserEntity.builder().id("123").build()
//        )));
        System.out.println(new BCryptPasswordEncoder().encode("123"));
        System.out.println(new BCryptPasswordEncoder().matches("123", "$2a$10$qbCJB7znYS/KD0sFR8f.C.o97a2PYFSGC8KyiKD.nYG7ZT2gaGm2y"));
    }

}
