package com.weng.messaging.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.UUID;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
public class RequestLoggingFilter extends OncePerRequestFilter {

    public static final String REQUEST_UUID_KEY = "requestUUID";

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return !hasText(path) || path.startsWith("/actuator") || path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String requestUUID = UUID.randomUUID().toString();

        // Put the UUID in MDC
        MDC.put(REQUEST_UUID_KEY, requestUUID);

        // Wrap request and response for logging purposes
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            logServerRequest(wrappedRequest);

            chain.doFilter(request, response);

            logServerResponse(wrappedResponse, startTime);
        } finally {
            wrappedResponse.copyBodyToResponse();
            // Clear the MDC after the request has been processed
            MDC.remove(REQUEST_UUID_KEY);
        }
    }

    private void logServerRequest(ContentCachingRequestWrapper request) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();

        log.info("SERVER-REQ: Method: {}, URI: {}, Query: {}",
                method, uri, queryString != null ? queryString : "[Empty Query]");
    }

    private void logServerResponse(ContentCachingResponseWrapper response, long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        log.info("SERVER-RES: Duration: {} ms, Status: {}",
                duration, response.getStatus());
    }
}
