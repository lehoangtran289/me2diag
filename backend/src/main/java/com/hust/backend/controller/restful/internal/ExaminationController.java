package com.hust.backend.controller.restful.internal;

import com.hust.backend.aop.AuthRequired;
import com.hust.backend.application.KDclassification.service.KDCExamService;
import com.hust.backend.application.picturefuzzyset.service.PFSExamService;
import com.hust.backend.application.picturefuzzyset.service.PFSService;
import com.hust.backend.constant.ApplicationEnum;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.dto.response.ExaminationResponseDTO;
import com.hust.backend.factory.GeneralResponse;
import com.hust.backend.factory.PagingInfo;
import com.hust.backend.factory.ResponseFactory;
import com.hust.backend.service.auth.JwtService;
import com.hust.backend.service.business.ExamService;
import com.hust.backend.service.business.PagingConverterService;
import com.hust.backend.service.business.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Slf4j
@Validated
@RestController
@RequestMapping("${app.application-context-name}/api/v1/examinations")
public class ExaminationController {
    private final ResponseFactory responseFactory;
    private final JwtService jwtService;
    private final PFSService pfsService;
    private final PatientService patientService;
    private final PFSExamService pfsExamService;
    private final KDCExamService kdcExamService;
    private final ExamService examService;
    private final PagingConverterService pageService;

    public ExaminationController(ResponseFactory responseFactory,
                                 JwtService jwtService,
                                 PFSService pfsService,
                                 PatientService patientService,
                                 PFSExamService pfsExamService,
                                 KDCExamService kdcExamService,
                                 ExamService examService,
                                 PagingConverterService pageService) {
        this.responseFactory = responseFactory;
        this.jwtService = jwtService;
        this.pfsService = pfsService;
        this.patientService = patientService;
        this.pfsExamService = pfsExamService;
        this.kdcExamService = kdcExamService;
        this.examService = examService;
        this.pageService = pageService;
    }

    @GetMapping("/{examinationId}")
    @AuthRequired(roles = UserRoleEnum.USER)
    public ResponseEntity<GeneralResponse<Object>> getExaminationByAppIdAndExamId(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable @NotBlank(message = "examination id must not be blank") String examinationId
    ) {
        return responseFactory.success(examService.getExamination(examinationId));
    }

    @GetMapping
    @AuthRequired(roles = UserRoleEnum.USER)
    public ResponseEntity<GeneralResponse<PagingInfo<ExaminationResponseDTO>>> getAllExaminations(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestParam(required = false) String query,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String[] sort
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, pageService.from(sort));
        return responseFactory.success(examService.getAllExaminations(query, pageable));
    }
}
