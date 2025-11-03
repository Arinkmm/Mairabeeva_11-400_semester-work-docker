package com.racetalk.web.servlet;

import com.racetalk.entity.User;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "Login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

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
        req.getRequestDispatcher("/templates/login.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String usernameInput = req.getParameter("username");
            String passwordInput = req.getParameter("password");

            Optional<User> userOpt = userService.loginUser(usernameInput, passwordInput);
            if (userOpt.isPresent()) {
                User user = userOpt.get();

                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("user", user);
                httpSession.setMaxInactiveInterval(60 * 60);

                resp.sendRedirect(req.getContextPath() + "/main");
            } else {
                req.setAttribute("LoginErrorMessage", "Неверное имя пользователя или пароль");
                req.getRequestDispatcher("/templates/login.ftl").forward(req, resp);
            }
        } catch (ServiceException e) {
            logger.error("Error during login process", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}