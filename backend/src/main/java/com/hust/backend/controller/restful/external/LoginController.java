package com.hust.backend.controller.restful.external;

import com.hust.backend.dto.request.ResetPasswordRequestDTO;
import com.hust.backend.dto.request.TokenRefreshRequestDTO;
import com.hust.backend.dto.request.UserLoginRequestDTO;
import com.hust.backend.dto.request.UserSignupRequestDTO;
import com.hust.backend.dto.response.RenewTokenResponseDTO;
import com.hust.backend.dto.response.UserLoginResponseDTO;
import com.hust.backend.factory.GeneralResponse;
import com.hust.backend.factory.ResponseFactory;
import com.hust.backend.service.auth.UserAuthenticationService;
import com.hust.backend.service.business.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequestMapping("${app.application-context-name}/api/v1")
public class LoginController {

    private final ResponseFactory responseFactory;
    private final UserAuthenticationService userAuthenticationService;
    private final UserService userService;

    public LoginController(ResponseFactory responseFactory,
                           UserAuthenticationService userAuthenticationService,
                           UserService userService) {
        this.responseFactory = responseFactory;
        this.userAuthenticationService = userAuthenticationService;
        this.userService = userService;
    }

    @PostMapping( "/login")
    public ResponseEntity<GeneralResponse<UserLoginResponseDTO>> login(
            @Valid @RequestBody UserLoginRequestDTO request
    ) {
        return responseFactory.success(userAuthenticationService.buildLoginSuccessResponse(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<GeneralResponse<RenewTokenResponseDTO>> refreshAccessToken(
            @Valid @RequestBody TokenRefreshRequestDTO request
    ) {
        return responseFactory.success(userAuthenticationService.renewAccessToken(request));
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

    @PostMapping("/user/sign-up")
    public ResponseEntity<GeneralResponse<String>> signup(
            @Valid @RequestBody UserSignupRequestDTO request
    ) {
        userService.registerUser(request);
        return responseFactory.success();
    }
}
