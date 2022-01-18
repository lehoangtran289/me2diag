package com.hust.backend.controller.restful.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hust.backend.exception.InternalException;
import com.hust.backend.response.CommonResponse;
import com.hust.backend.response.Response;
import com.hust.backend.response.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.application-context-name}/public/test")
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final ObjectMapper jacksonObjectMapper;

    @GetMapping()
    public ResponseEntity<CommonResponse<Object>> test() {
//        return ResponseUtils.ok(new TestDTO());
        throw new InternalException("Invalid input, object list size != distribution list size");
    }
}
