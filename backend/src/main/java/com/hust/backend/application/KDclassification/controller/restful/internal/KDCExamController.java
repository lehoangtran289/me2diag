package com.hust.backend.application.KDclassification.controller.restful.internal;

import com.hust.backend.factory.ResponseFactory;
import com.hust.backend.service.auth.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("${app.application-context-name}/api/v1/kdc")
public class KDCExamController {
    private final ResponseFactory responseFactory;
    private final JwtService jwtService;

    public KDCExamController(ResponseFactory responseFactory,
                             JwtService jwtService) {
        this.responseFactory = responseFactory;
        this.jwtService = jwtService;
    }
}