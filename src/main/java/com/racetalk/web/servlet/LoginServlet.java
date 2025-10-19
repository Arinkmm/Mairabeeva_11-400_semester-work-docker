package com.racetalk.web.servlet;

import com.racetalk.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "Login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String usernameInput = req.getParameter("username");
        String passwordInput = req.getParameter("password");

        if (userService.loginUser(usernameInput, passwordInput).isPresent()) {
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("user", usernameInput);
            httpSession.setMaxInactiveInterval(60 * 60);

            Cookie cookie = new Cookie("user", usernameInput);
            cookie.setMaxAge(24 * 60 * 60);

            resp.addCookie(cookie);

            resp.sendRedirect(req.getContextPath() + "/main");
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}