package com.hust.backend.controller.restful.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hust.backend.aop.AuthRequired;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.dto.request.UserInfoUpdateRequestDTO;
import com.hust.backend.dto.request.UserRegisterRequestDTO;
import com.hust.backend.dto.response.UserInfoResponseDTO;
import com.hust.backend.exception.NotValidException;
import com.hust.backend.factory.GeneralResponse;
import com.hust.backend.factory.PagingInfo;
import com.hust.backend.factory.ResponseFactory;
import com.hust.backend.model.token.AccessTokenPayload;
import com.hust.backend.service.auth.JwtService;
import com.hust.backend.service.business.PagingConverterService;
import com.hust.backend.service.business.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("${app.application-context-name}/api/v1/user")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final ResponseFactory responseFactory;
    private final PagingConverterService pageService;

    public UserController(UserService userService,
                          JwtService jwtService,
                          ResponseFactory responseFactory,
                          PagingConverterService pageService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.responseFactory = responseFactory;
        this.pageService = pageService;
    }

    @DeleteMapping("/{userId}/deactivate")
    @AuthRequired(roles = {UserRoleEnum.ADMIN, UserRoleEnum.USER})
    public ResponseEntity<GeneralResponse<UserInfoResponseDTO>> deactivateUser(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable @NotBlank(message = "userId must not be blank") String userId
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
        userService.deactivateUser(userId);
        return responseFactory.success();
    }

    @PostMapping("/{userId}/activate")
    @AuthRequired(roles = {UserRoleEnum.ADMIN, UserRoleEnum.USER})
    public ResponseEntity<GeneralResponse<UserInfoResponseDTO>> activateUser(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable @NotBlank(message = "userId must not be blank") String userId
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
        userService.activateUser(userId);
        return responseFactory.success();
    }

    @PostMapping("/register")
    @AuthRequired(roles = UserRoleEnum.ADMIN)
    public ResponseEntity<GeneralResponse<UserInfoResponseDTO>> registerNewUser(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @Valid @RequestBody UserRegisterRequestDTO request
    ) {
        userService.registerUser(request);
        return responseFactory.success();
    }

    @GetMapping("/me")
    @AuthRequired(roles = {UserRoleEnum.USER, UserRoleEnum.EXPERT})
    public ResponseEntity<GeneralResponse<UserInfoResponseDTO>> getUserInfo(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) throws InvalidKeySpecException, NoSuchAlgorithmException, JsonProcessingException {
        // validate userId
        AccessTokenPayload payload = jwtService.parse(authToken, AccessTokenPayload.class);
        return responseFactory.success(userService.getUserInfo(payload.getSubject()));
    }

    // TODO: admin check user profile

    @GetMapping
    @AuthRequired(roles = UserRoleEnum.ADMIN)
    public ResponseEntity<GeneralResponse<PagingInfo<UserInfoResponseDTO>>> getAllUsers(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestParam(required = false) String query,
            @RequestParam(value = "roles", required = false) List<String> roles,
            @RequestParam(value = "isEnable", required = false) Boolean isEnable,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String[] sort
    ) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, pageService.from(sort));
        return responseFactory.success(userService.getAllUsers(query, roles, isEnable, pageable));
    }

    @PatchMapping(value = "/{userId}", consumes = "multipart/form-data")
    @AuthRequired(roles = {UserRoleEnum.ADMIN, UserRoleEnum.USER, UserRoleEnum.EXPERT})
    public ResponseEntity<GeneralResponse<UserInfoResponseDTO>> updateMyUserInfo(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable @NotBlank(message = "userId must not be blank") String userId,
            @ModelAttribute UserInfoUpdateRequestDTO request
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
        return responseFactory.success(userService.updateUserInfo(userId, request));
    }
}
