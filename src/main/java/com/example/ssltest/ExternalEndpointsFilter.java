package com.example.ssltest;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class ExternalEndpointsFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(ExternalEndpointsFilter.class);
    private final int externalPort;
    private final  Set<String> externalRoutes;
    // TODO: переделать на что-то более удобоваримоеs
    private static final String BAD_REQUEST = String.format("{\"code\":%d,\"error\":true,\"errorMessage\":\"%s\"}",
            HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());

    public ExternalEndpointsFilter(int externalPort, Set<String> externalRoutes) {
        this.externalPort = externalPort;
        this.externalRoutes = externalRoutes;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        boolean isExternalPort = servletRequest.getLocalPort() == externalPort;
        boolean isExternalApi = externalRoutes.contains(((HttpServletRequest) servletRequest).getRequestURI());
        if ((isExternalPort  && !isExternalApi) || (!isExternalPort && isExternalApi)){
            log.debug("Wrong api usage. TODO");
            ((HttpServletResponse) servletResponse).setStatus(HttpStatus.BAD_REQUEST.value());
            servletResponse.getOutputStream().write(BAD_REQUEST.getBytes(StandardCharsets.UTF_8));
            servletResponse.getOutputStream().close();
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}