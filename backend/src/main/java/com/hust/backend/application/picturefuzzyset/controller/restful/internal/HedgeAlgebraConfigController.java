package com.hust.backend.application.picturefuzzyset.controller.restful.internal;

import com.hust.backend.aop.AuthRequired;
import com.hust.backend.application.picturefuzzyset.dto.request.HedgeAlgebraConfigRequestDTO;
import com.hust.backend.application.picturefuzzyset.dto.response.LinguisticDomainDTO;
import com.hust.backend.application.picturefuzzyset.service.HedgeAlgebraService;
import com.hust.backend.application.picturefuzzyset.service.PictureFuzzyRelationService;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.factory.GeneralResponse;
import com.hust.backend.factory.ResponseFactory;
import com.hust.backend.service.auth.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("${app.application-context-name}/api/v1/hedge-algebra")
public class HedgeAlgebraConfigController {
    private final ResponseFactory responseFactory;
    private final JwtService jwtService;
    private final PictureFuzzyRelationService pfsService;
    private final HedgeAlgebraService hedgeAlgebraService;

    public HedgeAlgebraConfigController(ResponseFactory responseFactory, JwtService jwtService,
                                        PictureFuzzyRelationService pfsService,
                                        HedgeAlgebraService hedgeAlgebraService) {
        this.responseFactory = responseFactory;
        this.jwtService = jwtService;
        this.pfsService = pfsService;
        this.hedgeAlgebraService = hedgeAlgebraService;
    }

    @GetMapping
    @AuthRequired(roles = UserRoleEnum.USER)
    public ResponseEntity<GeneralResponse<List<LinguisticDomainDTO>>> getAllLinguisticDomainElements(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        return responseFactory.success(
                hedgeAlgebraService.getAllLinguisticDomainElements()
        );
    }

    @PostMapping("/config")
    @AuthRequired(roles = UserRoleEnum.ADMIN)
    public ResponseEntity<GeneralResponse<List<LinguisticDomainDTO>>> changeHedgeAlgebraConfigs(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @Valid @RequestBody HedgeAlgebraConfigRequestDTO request
    ) {
        return responseFactory.success(
                hedgeAlgebraService.changeHedgeAlgebraConfigs(request)
        );
    }
}
