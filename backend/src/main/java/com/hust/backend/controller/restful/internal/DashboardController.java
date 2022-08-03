package com.hust.backend.controller.restful.internal;

import com.hust.backend.aop.AuthRequired;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.dto.response.DashboardInfoResponseDTO;
import com.hust.backend.factory.GeneralResponse;
import com.hust.backend.factory.ResponseFactory;
import com.hust.backend.service.business.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("${app.application-context-name}/api/v1/dashboard")
public class DashboardController {
    private final ResponseFactory responseFactory;
    private final DashboardService dashboardService;

    public DashboardController(ResponseFactory responseFactory, DashboardService dashboardService) {
        this.responseFactory = responseFactory;
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @AuthRequired(roles = { UserRoleEnum.ADMIN, UserRoleEnum.USER, UserRoleEnum.EXPERT })
    public ResponseEntity<GeneralResponse<DashboardInfoResponseDTO>> getDashboardInfo(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        return responseFactory.success(dashboardService.getGeneralInfo());
    }
}
