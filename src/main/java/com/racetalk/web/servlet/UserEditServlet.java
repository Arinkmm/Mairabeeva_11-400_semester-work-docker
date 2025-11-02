package com.racetalk.web.servlet;

import com.racetalk.entity.User;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;


@WebServlet(name = "UserEdit", urlPatterns = "/user/edit")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class UserEditServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserEditServlet.class);
    private UserService userService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
        if (userService == null) throw new IllegalStateException("UserService not initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        req.setAttribute("user", currentUser);
        req.getRequestDispatcher("/templates/user_edit.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User currentUser = (User) req.getSession().getAttribute("user");

            String username = req.getParameter("username");
            String currentPassword = req.getParameter("currentPassword");
            String newPassword = req.getParameter("newPassword");
            String confirmPassword = req.getParameter("confirmPassword");
            String status = req.getParameter("status");

            if (newPassword != null && !newPassword.isEmpty()) {
                if (!newPassword.equals(confirmPassword)) {
                    req.setAttribute("EditErrorMessage", "Пароли не совпадают");
                    req.getRequestDispatcher("/templates/user_edit.ftl").forward(req, resp);
                    return;
                }
                if (!userService.verifyPassword(currentUser, currentPassword)) {
                    req.setAttribute("EditErrorMessage", "Текущий пароль неверный");
                    req.getRequestDispatcher("/templates/user_edit.ftl").forward(req, resp);
                    return;
                }
                if (!userService.validatePassword(newPassword)) {
                    req.setAttribute("EditErrorMessage", "Пароль не соответствует требованиям");
                    req.getRequestDispatcher("/templates/user_edit.ftl").forward(req, resp);
                    return;
                }
                currentUser.setPassword(userService.hashPassword(newPassword));
            }

            currentUser.setUsername(username);
            currentUser.setStatus(status);

            Part photoPart = req.getPart("photo");
            InputStream photoInputStream = photoPart.getInputStream();

            userService.editUser(currentUser, photoInputStream);
            resp.sendRedirect(req.getContextPath() + "/user/profile/" + currentUser.getId());

        } catch (ServiceException e) {
            logger.error("Failed to update user profile", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}