package com.hust.backend.controller.restful.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hust.backend.aop.AuthRequired;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.dto.request.*;
import com.hust.backend.dto.response.RenewTokenResponseDTO;
import com.hust.backend.dto.response.UserLoginResponseDTO;
import com.hust.backend.exception.NotValidException;
import com.hust.backend.factory.GeneralResponse;
import com.hust.backend.factory.ResponseFactory;
import com.hust.backend.model.token.AccessTokenPayload;
import com.hust.backend.service.auth.JwtService;
import com.hust.backend.service.auth.UserAuthenticationService;
import com.hust.backend.service.business.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Slf4j
@Validated
@RestController
@RequestMapping("${app.application-context-name}/api/v1")
public class LoginController {
    private final ResponseFactory responseFactory;
    private final UserAuthenticationService userAuthenticationService;
    private final UserService userService;
    private final JwtService jwtService;

    public LoginController(ResponseFactory responseFactory,
                           UserAuthenticationService userAuthenticationService,
                           UserService userService,
                           JwtService jwtService) {
        this.responseFactory = responseFactory;
        this.userAuthenticationService = userAuthenticationService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse<UserLoginResponseDTO>> login(
            @Valid @RequestBody UserLoginRequestDTO request
    ) {
        return responseFactory.success(userAuthenticationService.buildLoginSuccessResponse(request));
    }

    // TODO: hamdle disabled user
    @PostMapping("/refresh-token")
    public ResponseEntity<GeneralResponse<RenewTokenResponseDTO>> refreshAccessToken(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String accessToken,
            @Valid @RequestBody TokenRefreshRequestDTO request
    ) {
        return responseFactory.success(userAuthenticationService.renewAccessToken(accessToken, request.getRefreshToken()));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<GeneralResponse<String>> forgotPassword(@RequestParam String email) {
        userAuthenticationService.sendPasswordResetToken(email);
        return responseFactory.success();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<GeneralResponse<Boolean>> resetPassword(
            @Valid @RequestBody ResetPasswordRequestDTO request
    ) {
        userAuthenticationService.resetPassword(request);
        return responseFactory.success();
    }

    @PostMapping("/user/{userId}/change-password")
    @AuthRequired(roles = {UserRoleEnum.ADMIN, UserRoleEnum.USER, UserRoleEnum.EXPERT})
    public ResponseEntity<GeneralResponse<Boolean>> changePassword(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable @NotBlank(message = "userId must not be blank") String userId,
            @Valid @RequestBody ChangePasswordRequestDTO request
    ) throws InvalidKeySpecException, NoSuchAlgorithmException, JsonProcessingException {
        // validate userId
        AccessTokenPayload payload = jwtService.parse(authToken, AccessTokenPayload.class);
        if (
                !payload.getRoles().contains(UserRoleEnum.ADMIN) && // ADMIN can update all user info
                    !StringUtils.equals(userId, payload.getSubject())
        ) {
            log.error("Invalid token, user id {} mismatched with token, id = {}", userId, payload.getSubject());
            throw new NotValidException("invalid token, id mismatched");
        }
        userAuthenticationService.changePassword(userId, request);
        return responseFactory.success();
    }

    // TODO: remove later. only ADMIN can signup user
    @PostMapping("/user/sign-up")
    public ResponseEntity<GeneralResponse<String>> signup(
            @Valid @RequestBody UserRegisterRequestDTO request
    ) {
        userService.registerUser(request);
        return responseFactory.success();
    }
}
