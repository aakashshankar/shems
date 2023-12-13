package com.shems.server.filter;

import com.shems.server.context.UserContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(UserFilter.class);

    private static final String HEADER = "x-user";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LOGGER.info("Intercepted request {} with user {}", request.getRequestURI(), request.getHeader(HEADER));
        String user = getUser(request);

        if (null == user) {
            if (permittedURIs(request.getRequestURI())) {
                filterChain.doFilter(request, response);
            } else {
                LOGGER.error("User not found for request accessing non permissible URI {}", request.getRequestURI());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("User not found for non permissible URI");
                response.getWriter().flush();
            }
            return;
        }

        UserContext.setCurrentUser(Long.valueOf(user));
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().startsWith("/webjars/")
                || request.getRequestURI().startsWith("/css/")
                || request.getRequestURI().startsWith("/js/")
                || request.getRequestURI().startsWith("/.ico");
    }

    private boolean permittedURIs(String requestURI) {
        return requestURI.matches(".*/user/register.*")
                || requestURI.matches("/api-docs.*");
    }

    private String getUser(HttpServletRequest request) {
        String header = request.getHeader(HEADER);
        if (null == header) {
            header = request.getParameter(HEADER);
        }
        return header;
    }
}
