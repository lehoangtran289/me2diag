package com.hust.backend.config.logging;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

@Slf4j
public class CustomURLFilter implements Filter {

    private static final String REQUEST_ID = "request_id";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String requestId = UUID.randomUUID().toString();
        servletRequest.setAttribute(REQUEST_ID, requestId);
        logServletRequest((HttpServletRequest) servletRequest, requestId);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }

    private void logServletRequest(HttpServletRequest servletRequest, String requestId) {
        if (servletRequest != null) {
            StringBuilder data = new StringBuilder();
            data.append("\nLOGGING REQUEST-----------------------------------\n")
                    .append("[REQUEST-ID]: ").append(requestId).append("\n")
                    .append("[PATH]: ").append(servletRequest.getRequestURI()).append("\n")
                    .append("[QUERIES]: ").append(servletRequest.getQueryString()).append("\n")
                    .append("[HEADERS]: ").append("\n");

            Enumeration<String> headerNames = servletRequest.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = headerNames.nextElement();
                String value = servletRequest.getHeader(key);
                data.append("---").append(key).append(" : ").append(value).append("\n");
            }
            data.append("LOGGING REQUEST-----------------------------------");

            log.info(data.toString());
        }
    }
}
