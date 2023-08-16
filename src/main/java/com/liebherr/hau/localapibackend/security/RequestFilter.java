package com.liebherr.hau.localapibackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Component
@Order(1)
public class RequestFilter extends AbstractSecurityFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse = applyHeaders(httpRequest, httpResponse);

        String uri = httpRequest.getRequestURI();
        LOGGER.info("FILTERING: {} FOR {}", kv("URI", uri), kv("IP", httpRequest.getRemoteAddr()));

        if (HttpMethod.OPTIONS.matches(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpStatus.OK.value());
            return;
        }

        boolean anonymousAccess = isAnonymousAccess(httpRequest);
        boolean anonymousAccessAllowed = isAnonymousAccessAllowed(uri, httpRequest);

        if (anonymousAccess && anonymousAccessAllowed) {
            chain.doFilter(httpRequest, httpResponse);

            return;
        }

        chain.doFilter(httpRequest, httpResponse);
    }
}
