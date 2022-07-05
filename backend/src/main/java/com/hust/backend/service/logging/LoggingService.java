package com.hust.backend.service.logging;

import com.hust.backend.utils.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
public class LoggingService {
    private static final String REQUEST_ID = "request_id";

    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        if (httpServletRequest.getRequestURI().contains("media")) {
            return;
        }
        Object requestId = httpServletRequest.getAttribute(REQUEST_ID);
        String data = "\nLOGGING REQUEST BODY-----------------------------------\n" +
                "[REQUEST-ID]: " + requestId + "\n" +
                "[BODY REQUEST]: " + "\n\n" +
                Common.toJson(body) +
                "\n\n" +
                "LOGGING REQUEST BODY-----------------------------------";

        log.info(data);
    }

    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
        if (httpServletRequest.getRequestURI().contains("media")) {
            return;
        }
        Object requestId = httpServletRequest.getAttribute(REQUEST_ID);
        String data = "\nLOGGING RESPONSE-----------------------------------\n" +
                "[REQUEST-ID]: " + requestId + "\n" +
                "[BODY RESPONSE]: " + "\n\n" +
                Common.toJson(body) +
                "\n\n" +
                "LOGGING RESPONSE-----------------------------------";
        log.info(data);
    }
}