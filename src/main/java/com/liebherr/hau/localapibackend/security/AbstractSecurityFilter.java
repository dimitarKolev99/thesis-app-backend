package com.liebherr.hau.localapibackend.security;

import com.liebherr.hau.localapibackend.resources.ApplicationProperties;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * Base class offering helpful methods to filters extending this class.
 */
public abstract class AbstractSecurityFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSecurityFilter.class);

//    @Autowired
//    ApplicationProperties applicationProperties;

    /**
     * This is the password for the "public" access. It is not really safe but better than nothing. And the called API is
     * kind of public.
     */
    private static final String PUBLIC_PASSWORD = "PublicPassword 6Y6494ct55E6rCV4";

    /**
     * Applies headers to the request. These are standard headers needed for the UI
     * to work.
     *
     * @param httpRequest  The request.
     * @param httpResponse The response.
     *
     * @return the response with headers set.
     */
    protected HttpServletResponse applyHeaders(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
        httpResponse.setIntHeader("Access-Control-Max-Age", 86400);
        httpResponse.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept, Authorization, X-B3-TraceId, " +
                        "Timezone, Context, Country, Sensors, Company"); // Custom headers
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

        return httpResponse;
    }

    /**
     * Some calls need to be anonymous. OPTIONS requests are an example. The
     * WebSockets are another since they authenticate differently.
     *
     * @param uri         The URI to invoke.
     * @param httpRequest The request object.
     *
     * @return true if an anonymous access is allowed.
     */
    protected boolean isAnonymousAccessAllowed(String uri, HttpServletRequest httpRequest) {
        // For OPTION requests (which are done by the UI) we allow access
        if (HttpMethod.OPTIONS.matches(httpRequest.getMethod())) {
            return true;
        }

        // The WebSocket handles security itself
        if (uri.startsWith("/ui/ws")) {
            LOGGER.info("WebSocket access allowed to {}", kv("URI", uri));

            return true;
        }

        // Special case, user needs to be able to access the semi public things.
        // User always can access the manuals, logged-in or not
        // User always can check the SM compatibility, logged-in or not
        if (uri.startsWith("/ui/public/")) {
            String authorization = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
            boolean allowed = PUBLIC_PASSWORD.equals(authorization);

            if (allowed) {
                LOGGER.info("Public access allowed to {}", kv("URI", uri));
                return true;
            }

            LOGGER.info("WebSocket access DENIED to {}", kv("URI", uri));
            return false;
        }

        // If there is no authorization we assume it's a swagger call and we only allow it if the configuration
        // says so.
//        if (applicationProperties.enableSwagger()) {
//            return true;
//        }

        return false;
    }

    /**
     * Checks whether this is a request with no authorization or with a "custom" authorization, meaning: Not Bearer.
     *
     * @param httpRequest The request.
     *
     * @return true if there is no (Bearer) authorization passed.
     */
    protected boolean isAnonymousAccess(HttpServletRequest httpRequest) {
        return httpRequest.getHeader(HttpHeaders.AUTHORIZATION) == null || !httpRequest.getHeader(HttpHeaders.AUTHORIZATION).startsWith("Bearer");
    }

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
        // nothing to init
    }

    @Override
    public void destroy() {
        // nothing to destroy
    }
}
