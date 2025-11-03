package com.racetalk.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", filterName = "Authentication")
public class AuthenticationFilter implements Filter {
    private static final String[] excludedPaths = {"/login", "/sign_up", "/index", "/assets/", "/validate/"};
    private static final String rootPath = "/";

    private boolean isExcluded(String path) {
        if (path.equals(rootPath)) {
            return true;
        }
        for (String exclude : excludedPaths) {
            if (path.startsWith(exclude)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (isExcluded(path)) {
            chain.doFilter(request, response);
        } else {
            HttpSession session = req.getSession(false);

            if (session == null) {
                res.sendRedirect(req.getContextPath() + "/login");
            } else {
                chain.doFilter(request, response);
            }
        }
    }
}

