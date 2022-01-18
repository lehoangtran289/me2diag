package com.hust.backend.config.filter;

import com.hust.backend.constant.TrackingContextEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Configuration
@Slf4j
public class LogCorrelationFilter extends OncePerRequestFilter {
    @Value("${app.application-context-name")
    private String applicationShortName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        long time = System.currentTimeMillis();
        generateCorrelationIdIfNotExists(
                request.getHeader(TrackingContextEnum.X_CORRELATION_ID.getHeaderKey()));
        response.setHeader(TrackingContextEnum.X_CORRELATION_ID.getHeaderKey(),
                ThreadContext.get(TrackingContextEnum.X_CORRELATION_ID.getThreadKey()));
        chain.doFilter(request, response);
        log.info("{}: {} ms ", request.getRequestURI(), System.currentTimeMillis() - time);
        ThreadContext.clearAll();
    }

    private void generateCorrelationIdIfNotExists(String xCorrelationId) {
        String uuId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String nameWhenNull = (applicationShortName + "-" + uuId).trim();
        String correlationId = StringUtils.isEmpty(xCorrelationId) ? nameWhenNull : xCorrelationId;
        ThreadContext.put(TrackingContextEnum.X_CORRELATION_ID.getThreadKey(), correlationId);
    }
}