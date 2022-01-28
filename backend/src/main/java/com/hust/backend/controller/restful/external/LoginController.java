package com.hust.backend.controller.restful.external;

import com.hust.backend.dto.request.UserLoginRequestDTO;
import com.hust.backend.dto.response.UserLoginResponseDTO;
import com.hust.backend.factory.GeneralResponse;
import com.hust.backend.factory.ResponseFactory;
import com.hust.backend.service.UserAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequestMapping("${app.application-context-name}/api/v1")
public class LoginController {

    private final ResponseFactory responseFactory;
    private final UserAuthenticationService userAuthenticationService;

    public LoginController(ResponseFactory responseFactory, UserAuthenticationService userAuthenticationService) {
        this.responseFactory = responseFactory;
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping( "/login")
    public ResponseEntity<GeneralResponse<UserLoginResponseDTO>> login(
            @Valid @RequestBody UserLoginRequestDTO request
    ) {
        return responseFactory.success(userAuthenticationService.buildLoginSuccessResponse(request));
    }

//    @PostMapping("/refresh-token")
//    public ResponseEntity<GeneralResponse<TokenResponseDTO>> refreshAccessToken(
//            @Valid @RequestBody RenewTokenRequestDTO request
//    ) {
//        TokenInfo accessTokenInfo = userAuthenticationService.renewAccessToken(request);
//        return responseFactory.success(
//                TokenResponseDTO.builder()
//                        .accessToken(accessTokenInfo.getToken())
//                        .refreshToken(request.getRefreshToken())
//                        .payload(PayLoadResponseDTO.toDTO(accessTokenInfo.getClaims()))
//                        .build()
//        );
//    }
}
