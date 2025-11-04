package com.racetalk.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "UsernameValidation", urlPatterns = "/validate/username")
public class UsernameValidationServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UsernameUniqueServlet.class);
    private final ObjectMapper mapper = new ObjectMapper();

    private UserService userService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
        if (userService == null) {
            throw new IllegalStateException("UserService not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String username = req.getParameter("username");

            boolean valid = userService.validateUsername(username);

            resp.setContentType("application/json;charset=UTF-8");

            Map<String, Boolean> result = Map.of("valid", valid);
            mapper.writeValue(resp.getWriter(), result);
        } catch (ServiceException e) {
            logger.error("Failed to check username validation", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}
