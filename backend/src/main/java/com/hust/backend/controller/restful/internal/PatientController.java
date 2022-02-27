package com.hust.backend.controller.restful.internal;

import com.hust.backend.aop.AuthRequired;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.dto.request.PatientRegisterRequestDTO;
import com.hust.backend.dto.response.ExamIDListResponseDTO;
import com.hust.backend.dto.response.PatientInfoResponseDTO;
import com.hust.backend.factory.GeneralResponse;
import com.hust.backend.factory.PagingInfo;
import com.hust.backend.factory.ResponseFactory;
import com.hust.backend.service.auth.JwtService;
import com.hust.backend.service.business.ExaminationService;
import com.hust.backend.service.business.PagingConverterService;
import com.hust.backend.service.business.PatientService;
import com.hust.backend.service.business.PictureFuzzyRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Slf4j
@Validated
@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("${app.application-context-name}/api/v1/patient")
public class PatientController {

    private final ResponseFactory responseFactory;
    private final JwtService jwtService;
    private final PictureFuzzyRelationService pfsService;
    private final PatientService patientService;
    private final ExaminationService examinationService;
    private final PagingConverterService pageService;

    public PatientController(ResponseFactory responseFactory,
                             PictureFuzzyRelationService pfsService,
                             PatientService patientService,
                             JwtService jwtService,
                             ExaminationService examinationService,
                             PagingConverterService pageService) {
        this.responseFactory = responseFactory;
        this.pfsService = pfsService;
        this.patientService = patientService;
        this.jwtService = jwtService;
        this.examinationService = examinationService;
        this.pageService = pageService;
    }

    //TODO: POST return entity not void
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<GeneralResponse<String>> registerPatient(
            @Valid @ModelAttribute PatientRegisterRequestDTO request
    ) {
        patientService.registerPatient(request);
        return responseFactory.success();
    }

    @GetMapping
    @AuthRequired(roles = UserRoleEnum.USER)
    public ResponseEntity<GeneralResponse<PagingInfo<PatientInfoResponseDTO>>> getAllPatient(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestParam(required = false) String query,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String[] sort
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, pageService.from(sort));
        return responseFactory.success(patientService.getALlPatients(query, pageable));
    }

    @GetMapping("/{patientId}")
    @AuthRequired(roles = UserRoleEnum.USER)
    public ResponseEntity<GeneralResponse<PatientInfoResponseDTO>> getPatientInfo(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable @NotBlank(message = "patient id must not be blank") String patientId
    ) {
        return responseFactory.success(patientService.getPatient(patientId));
    }

    @GetMapping("/{patientId}/examinations")
    @AuthRequired(roles = UserRoleEnum.USER)
    public ResponseEntity<GeneralResponse<List<ExamIDListResponseDTO>>> getPatientExaminations(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable @NotBlank(message = "patient id must not be blank") String patientId
    ) {
        return responseFactory.success(examinationService.getPatientExaminations(patientId));
    }

}
