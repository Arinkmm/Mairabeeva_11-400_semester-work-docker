package com.racetalk.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.racetalk.util.UsernameValidator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "UsernameValidation", urlPatterns = "/validate/username")
public class UsernameValidationServlet extends HttpServlet {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");

        boolean valid = UsernameValidator.isValid(username);

        resp.setContentType("application/json;charset=UTF-8");

        Map<String, Boolean> result = Map.of("valid", valid);
        mapper.writeValue(resp.getWriter(), result);
    }
}
