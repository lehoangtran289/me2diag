package com.hust.backend.controller.restful.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hust.backend.aop.AuthRequired;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.factory.GeneralResponse;
import com.hust.backend.factory.ResponseFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.application-context-name}/public/test")
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final ObjectMapper jacksonObjectMapper;
    private final ResponseFactory responseFactory;

    @GetMapping()
    @AuthRequired(roles = UserRoleEnum.ADMIN)
    public ResponseEntity<GeneralResponse<Object>> test(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorization
    ) {
//        throw new NotFoundException(String.class, "1");
//        throw new UnauthorizedException("Not Authorized: " + 1, "1");
        return responseFactory.success(new TestDTO());
    }
}
