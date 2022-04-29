package com.hust.backend.controller.restful.internal;

import com.hust.backend.aop.AuthRequired;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.dto.response.ApplicationResponseDTO;
import com.hust.backend.factory.GeneralResponse;
import com.hust.backend.factory.ResponseFactory;
import com.hust.backend.service.auth.JwtService;
import com.hust.backend.service.business.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("${app.application-context-name}/api/v1/application")
public class ApplicationController {
    private final ResponseFactory responseFactory;
    private final ApplicationService applicationService;
    private final JwtService jwtService;

    public ApplicationController(ResponseFactory responseFactory,
                                 ApplicationService applicationService,
                                 JwtService jwtService) {
        this.responseFactory = responseFactory;
        this.applicationService = applicationService;
        this.jwtService = jwtService;
    }

    @GetMapping
    @AuthRequired(roles = UserRoleEnum.ADMIN)
    public ResponseEntity<GeneralResponse<List<ApplicationResponseDTO>>> getAllApplications(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        return responseFactory.success(
                applicationService.getAllApplications()
        );
    }
}
