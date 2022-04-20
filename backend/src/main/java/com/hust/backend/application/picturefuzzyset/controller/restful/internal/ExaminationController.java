package com.hust.backend.application.picturefuzzyset.controller.restful.internal;

import com.hust.backend.aop.AuthRequired;
import com.hust.backend.application.picturefuzzyset.dto.response.PatientExaminationResponseDTO;
import com.hust.backend.application.picturefuzzyset.service.ExaminationService;
import com.hust.backend.application.picturefuzzyset.service.PatientService;
import com.hust.backend.application.picturefuzzyset.service.PictureFuzzyRelationService;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.factory.GeneralResponse;
import com.hust.backend.factory.PagingInfo;
import com.hust.backend.factory.ResponseFactory;
import com.hust.backend.service.auth.JwtService;
import com.hust.backend.service.business.PagingConverterService;
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
@RequestMapping("${app.application-context-name}/api/v1/examination")
public class ExaminationController {

    private final ResponseFactory responseFactory;
    private final JwtService jwtService;
    private final PictureFuzzyRelationService pfsService;
    private final PatientService patientService;
    private final ExaminationService examinationService;
    private final PagingConverterService pageService;

    public ExaminationController(ResponseFactory responseFactory,
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

    @GetMapping("/{id}")
    @AuthRequired(roles = UserRoleEnum.USER)
    public ResponseEntity<GeneralResponse<PatientExaminationResponseDTO>> getExamination(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable @NotBlank(message = "examination id must not be blank") String id
    ) {
        return responseFactory.success(examinationService.getExamination(id));
    }

    @GetMapping
    @AuthRequired(roles = UserRoleEnum.USER)
    public ResponseEntity<GeneralResponse<PagingInfo<PatientExaminationResponseDTO>>> getAllExaminations(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestParam(required = false) String query,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String[] sort
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, pageService.from(sort));
        return responseFactory.success(examinationService.getAllExaminations(query, pageable));
    }
}
