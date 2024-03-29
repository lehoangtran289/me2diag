package com.hust.backend.controller.restful.internal;

import com.hust.backend.aop.AuthRequired;
import com.hust.backend.constant.ApplicationEnum;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.dto.response.ExamResultResponseDTO;
import com.hust.backend.dto.response.ExaminationResponseDTO;
import com.hust.backend.factory.GeneralResponse;
import com.hust.backend.factory.PagingInfo;
import com.hust.backend.factory.ResponseFactory;
import com.hust.backend.service.business.ExamService;
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
@RequestMapping("${app.application-context-name}/api/v1/examinations")
public class ExaminationController {
    private final ResponseFactory responseFactory;
    private final ExamService examService;
    private final PagingConverterService pageService;

    public ExaminationController(ResponseFactory responseFactory,
                                 ExamService examService,
                                 PagingConverterService pageService) {
        this.responseFactory = responseFactory;
        this.examService = examService;
        this.pageService = pageService;
    }

    @GetMapping("/{examinationId}")
    @AuthRequired(roles = UserRoleEnum.USER)
    public ResponseEntity<GeneralResponse<ExamResultResponseDTO>> getExaminationByAppIdAndExamId(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @PathVariable @NotBlank(message = "examination id must not be blank") String examinationId
    ) {
        return responseFactory.success(examService.getExamination(examinationId));
    }

    @GetMapping
    @AuthRequired(roles = UserRoleEnum.USER)
    public ResponseEntity<GeneralResponse<PagingInfo<ExaminationResponseDTO>>> getAllExaminations(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authToken,
            @RequestParam(required = false) ApplicationEnum appId,
            @RequestParam(required = false) String query,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String[] sort
    ) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, pageService.from(sort));
        return responseFactory.success(examService.getAllExaminations(appId, query, pageable));
    }
}
