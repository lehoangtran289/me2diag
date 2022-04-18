package com.hust.backend.controller.restful.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hust.backend.aop.AuthRequired;
import com.hust.backend.constant.ResponseStatusEnum;
import com.hust.backend.constant.SymptomEnum;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.dto.request.DiagnoseRequestDTO;
import com.hust.backend.dto.response.DiagnoseResponseDTO;
import com.hust.backend.entity.PatientSymptomEntity;
import com.hust.backend.exception.Common.BusinessException;
import com.hust.backend.factory.GeneralResponse;
import com.hust.backend.factory.ResponseFactory;
import com.hust.backend.model.PictureFuzzySet;
import com.hust.backend.model.token.AccessTokenPayload;
import com.hust.backend.service.auth.JwtService;
import com.hust.backend.service.business.PictureFuzzyRelationService;
import com.hust.backend.utils.PFSCommon;
import com.hust.backend.utils.ULID;
import com.hust.backend.utils.tuple.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Validated
@RestController
@RequestMapping("${app.application-context-name}/api/v1/pfs")
public class PictureFuzzyController {
    private final ResponseFactory responseFactory;
    private final JwtService jwtService;
    private final PictureFuzzyRelationService pfsService;

    public PictureFuzzyController(ResponseFactory responseFactory,
                                  PictureFuzzyRelationService pfsService,
                                  JwtService jwtService) {
        this.responseFactory = responseFactory;
        this.pfsService = pfsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/diagnose")
    @AuthRequired(roles = UserRoleEnum.USER)
    public ResponseEntity<GeneralResponse<DiagnoseResponseDTO>> diagnose(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @Valid @RequestBody DiagnoseRequestDTO request
    ) throws InvalidKeySpecException, NoSuchAlgorithmException, JsonProcessingException {
        // get userId
        AccessTokenPayload payload = jwtService.parse(authToken, AccessTokenPayload.class);
        String userId = payload.getSubject();

        // build input
        String examinationId = ULID.nextULID();

        List<PatientSymptomEntity> patientSymptoms = new ArrayList<>();
        for (Map.Entry<String, PictureFuzzySet> data : request.getSymptoms()) {
            PictureFuzzySet pfs = data.getValue();
            if (!PFSCommon.isPFSValid(pfs)) {
                log.error("PFS not valid, {}", pfs);
                throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "PFS not valid");
            }
            patientSymptoms.add(PatientSymptomEntity.builder()
                    .examinationId(examinationId)
                    .symptom(SymptomEnum.from(data.getKey()))
                    .pictureFuzzySet(pfs)
                    .build());
        }
        return responseFactory.success(
                pfsService.diagnose(examinationId, userId, request.getPatientId(), patientSymptoms)
        );
    }
}
