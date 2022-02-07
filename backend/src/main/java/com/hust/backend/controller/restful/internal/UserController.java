package com.hust.backend.controller.restful.internal;

import com.hust.backend.aop.AuthRequired;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.dto.request.UserInfoUpdateRequestDTO;
import com.hust.backend.factory.GeneralResponse;
import com.hust.backend.factory.ResponseFactory;
import com.hust.backend.service.business.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Slf4j
@Validated
@RestController
@RequestMapping("${app.application-context-name}/api/v1/user")
public class UserController {
    private final UserService userService;
    private final ResponseFactory responseFactory;

    public UserController(UserService userService, ResponseFactory responseFactory) {
        this.userService = userService;
        this.responseFactory = responseFactory;
    }

    @PatchMapping("{userId}")
    @AuthRequired(roles = UserRoleEnum.USER)
    public ResponseEntity<GeneralResponse<String>> updateUserInfo(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable @NotBlank(message = "userId must not be blank") String userId,
            @RequestBody UserInfoUpdateRequestDTO request
    ) {

        return responseFactory.success();
    }
}
