package com.hust.backend.controller.restful.internal;

import com.hust.backend.aop.AuthRequired;
import com.hust.backend.application.picturefuzzyset.service.PFSConfigService;
import com.hust.backend.constant.ApplicationEnum;
import com.hust.backend.constant.HedgeAlgebraEnum;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.dto.request.HedgeAlgebraConfigRequestDTO;
import com.hust.backend.dto.response.HedgeConfigResponseDTO;
import com.hust.backend.dto.response.LinguisticDomainResponseDTO;
import com.hust.backend.factory.GeneralResponse;
import com.hust.backend.factory.ResponseFactory;
import com.hust.backend.service.auth.JwtService;
import com.hust.backend.service.business.HedgeAlgebraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("${app.application-context-name}/api/v1/hedge-algebra")
public class HedgeAlgebraConfigController {
    private final ResponseFactory responseFactory;
    private final JwtService jwtService;
    private final PFSConfigService pfsConfigService;
    private final HedgeAlgebraService hedgeAlgebraService;

    public HedgeAlgebraConfigController(ResponseFactory responseFactory, JwtService jwtService,
                                        PFSConfigService pfsConfigService,
                                        HedgeAlgebraService hedgeAlgebraService) {
        this.responseFactory = responseFactory;
        this.jwtService = jwtService;
        this.pfsConfigService = pfsConfigService;
        this.hedgeAlgebraService = hedgeAlgebraService;
    }

    @GetMapping("/config")
    @AuthRequired(roles = {UserRoleEnum.USER, UserRoleEnum.EXPERT})
    public ResponseEntity<GeneralResponse<List<HedgeConfigResponseDTO>>> getAllHedgeAlgebraConfig(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestParam @NotBlank(message = "application id must not be blank") String appId
    ) {
        return responseFactory.success(
                hedgeAlgebraService.getAllHedgeConfigsElements(ApplicationEnum.from(appId))
        );
    }

    @GetMapping("/linguistic-domain")
    @AuthRequired(roles = {UserRoleEnum.USER, UserRoleEnum.EXPERT})
    public ResponseEntity<GeneralResponse<List<LinguisticDomainResponseDTO>>> getAllLinguisticDomainElements(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestParam @NotBlank(message = "application id must not be blank") String appId
    ) {
        return responseFactory.success(
                hedgeAlgebraService.getAllLinguisticDomainElements(ApplicationEnum.from(appId))
        );
    }

    @PutMapping("/config")
    @AuthRequired(roles = UserRoleEnum.EXPERT)
    public ResponseEntity<GeneralResponse<List<LinguisticDomainResponseDTO>>> changeHedgeAlgebraConfigs(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @Valid @RequestBody HedgeAlgebraConfigRequestDTO request
    ) {
        //validate request
        List<HedgeAlgebraEnum> hedges = new ArrayList<>();
        double sum = 0;
        for (Map.Entry<HedgeAlgebraEnum, Double> entry : request.getConfigs().entrySet()) {
            hedges.add(entry.getKey());
            sum += entry.getValue();
        }
        if (sum != 1) throw new IllegalArgumentException("Invalid fm sum of hedges");
        switch (request.getAppId()) {
            case PFS:
                if (hedges.size() != HedgeAlgebraEnum.pfsHedges.size() ||
                        !new HashSet<>(hedges).containsAll(HedgeAlgebraEnum.pfsHedges)) {
                    throw new IllegalArgumentException("Invalid hedges input pfs");
                }
                break;
            case KDC:
                if (hedges.size() != HedgeAlgebraEnum.kdcHedges.size() ||
                        !new HashSet<>(hedges).containsAll(HedgeAlgebraEnum.kdcHedges)) {
                    throw new IllegalArgumentException("Invalid hedges input kdc");
                }
                break;
        }

        return responseFactory.success(
                hedgeAlgebraService.changeHedgeAlgebraConfigs(request)
        );
    }
}
