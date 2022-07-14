package com.hust.backend.application.picturefuzzyset.controller.restful.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hust.backend.aop.AuthRequired;
import com.hust.backend.application.picturefuzzyset.constant.SymptomEnum;
import com.hust.backend.application.picturefuzzyset.dto.request.GeneralDiagnoseRequestDTO;
import com.hust.backend.application.picturefuzzyset.dto.request.SymptomDiagnoseConfigRequestDTO;
import com.hust.backend.application.picturefuzzyset.dto.response.PFSDiagnoseResponseDTO;
import com.hust.backend.application.picturefuzzyset.model.SymptomDiagnoseConfig;
import com.hust.backend.application.picturefuzzyset.service.PFSService;
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
@RequestMapping("${app.application-context-name}/api/v1/pfs")
public class PFSController {
    private final ResponseFactory responseFactory;
    private final JwtService jwtService;
    private final PFSService pfsService;

    public PFSController(ResponseFactory responseFactory,
                         PFSService pfsService,
                         JwtService jwtService) {
        this.responseFactory = responseFactory;
        this.pfsService = pfsService;
        this.jwtService = jwtService;
    }

    /**
     * The order of symptom elements defined in this api's request body MUST BE SIMILAR to those defined in {@link SymptomEnum}
     * @apiNote
     * {
     * 	"patient_id": "0123",
     * 	"symptoms": [
     *                {
     *             "TEMPERATURE" : {
     * 			    "positive": 0.7,
     * 			    "neutral": "VERY LOW",
     * 			    "negative": 0.1
     *            }
     *         },
     *        {
     *             "HEADACHE" : {
     * 			    "positive": 0.7,
     * 			    "neutral": 0.05,
     * 			    "negative": 0.2
     *            }
     *         },
     *         {
     *             "STOMACH_PAIN" : {
     * 			    "positive": 0.1,
     * 			    "neutral": 0.2,
     * 			    "negative": 0.6
     *            }
     *         },
     *        {
     *             "COUGH" : {
     * 			    "positive": 0.7,
     * 			    "neutral": 0.15,
     * 			    "negative": 0.1
     *            }
     *         },
     *         {
     *             "CHEST_PAIN" : {
     * 			    "positive": 0.2,
     * 			    "neutral": 0.3,
     * 			    "negative": 0.5
     *            }
     *         }
     * 	]
     * }
     */
    @PostMapping("/diagnose")
    @AuthRequired(roles = UserRoleEnum.USER)
    public ResponseEntity<GeneralResponse<PFSDiagnoseResponseDTO>> diagnoseWithHedgeAlgebra(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @Valid @RequestBody GeneralDiagnoseRequestDTO request
    ) throws InvalidKeySpecException, NoSuchAlgorithmException, JsonProcessingException {
        // get userId
        AccessTokenPayload payload = jwtService.parse(authToken, AccessTokenPayload.class);
        String userId = payload.getSubject();

        return responseFactory.success(pfsService.diagnose(userId, request));
    }

    @PutMapping("/config")
    @AuthRequired(roles = UserRoleEnum.EXPERT)
    public ResponseEntity<GeneralResponse<Boolean>> changePFSConfigs(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @Valid @RequestBody List<SymptomDiagnoseConfigRequestDTO> request
    ) {
        return responseFactory.success(pfsService.changePFSConfigs(request));
    }

    @GetMapping("/config")
    @AuthRequired(roles = UserRoleEnum.EXPERT)
    public ResponseEntity<GeneralResponse<List<SymptomDiagnoseConfig>>> getPFSConfigs(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken
    ) {
        return responseFactory.success(pfsService.getPFSConfigs());
    }
}
