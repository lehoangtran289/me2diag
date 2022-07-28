package com.hust.backend.application.KDclassification.controller.restful.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hust.backend.aop.AuthRequired;
import com.hust.backend.application.KDclassification.dto.request.KDCRequestDTO;
import com.hust.backend.application.KDclassification.dto.response.KDCDiagnoseResponseDTO;
import com.hust.backend.application.KDclassification.dto.response.KDCDomainResponseDTO;
import com.hust.backend.application.KDclassification.service.KDCExamService;
import com.hust.backend.application.KDclassification.service.KDCService;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.factory.GeneralResponse;
import com.hust.backend.factory.ResponseFactory;
import com.hust.backend.model.token.AccessTokenPayload;
import com.hust.backend.service.auth.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("${app.application-context-name}/api/v1/kdc")
public class KDCController {
    private final ResponseFactory responseFactory;
    private final JwtService jwtService;
    private final KDCExamService kdcExamService;
    private final KDCService kdcService;

    public KDCController(ResponseFactory responseFactory,
                         JwtService jwtService,
                         KDCExamService kdcExamService, KDCService kdcService) {
        this.responseFactory = responseFactory;
        this.jwtService = jwtService;
        this.kdcExamService = kdcExamService;
        this.kdcService = kdcService;
    }

    @PostMapping("/diagnose")
    @AuthRequired(roles = UserRoleEnum.USER)
    public ResponseEntity<GeneralResponse<KDCDiagnoseResponseDTO>> diagnoseWithHedgeAlgebra(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @Valid @RequestBody KDCRequestDTO request
    ) throws InvalidKeySpecException, NoSuchAlgorithmException, JsonProcessingException {
        // get userId
        AccessTokenPayload payload = jwtService.parse(authToken, AccessTokenPayload.class);
        String userId = payload.getSubject();

        return responseFactory.success(kdcExamService.diagnose(userId, request));
    }

    @GetMapping("/domain")
    @AuthRequired(roles = {UserRoleEnum.USER, UserRoleEnum.EXPERT})
    public ResponseEntity<GeneralResponse<List<KDCDomainResponseDTO>>> getAllKDCDomain(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        return responseFactory.success(kdcService.getAllKDCDomain());
    }
}