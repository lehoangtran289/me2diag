package com.hust.backend;

import com.hust.backend.constant.UserGenderEnum;
import com.hust.backend.dto.request.UserInfoUpdateRequestDTO;
import com.hust.backend.entity.UserEntity;
import com.hust.backend.service.auth.JwtService;
import com.hust.backend.utils.Common;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private JwtService jwtService;

//    @Test
    public void generateToken() {
//        System.out.println(jwtService.validateToken(jwtService.generateAccessToken(
//                UserEntity.builder().id("123").build()
//        )));
        System.out.println(new BCryptPasswordEncoder().encode("123"));
        System.out.println(new BCryptPasswordEncoder().matches("123", "$2a$10$qbCJB7znYS/KD0sFR8f.C.o97a2PYFSGC8KyiKD.nYG7ZT2gaGm2y"));
    }

    public static void main(String[] args) {
        UserInfoUpdateRequestDTO userDTO = UserInfoUpdateRequestDTO.builder()
                .firstName("hoang")
                .birthDate(new Date())
                .gender(UserGenderEnum.MALE)
                .build();
        UserEntity user = UserEntity.builder()
                .firstName("1")
                .lastName("2")
                .email("asdasd")
                .gender(UserGenderEnum.FEMALE)
                .build();
        Common.copyPropertiesIgnoreNull(userDTO, user);
        System.out.println(user);
    }

}
