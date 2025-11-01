package com.racetalk.web.servlet;

import com.cloudinary.utils.ObjectUtils;
import com.racetalk.entity.User;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.UserService;
import com.racetalk.util.PasswordHasherUtil;
import com.racetalk.util.PasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@WebServlet(name = "UserProfile", urlPatterns = "/user/profile/*")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class UserProfileServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserProfileServlet.class);

    private UserService userService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
        if (userService == null) {
            throw new IllegalStateException("UserService not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            req.setAttribute("errorMessage", "Id пользователя не найдено");
            req.setAttribute("statusCode", 400);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        String userIdStr = pathInfo.substring(1);
        int userId;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Неверный формат ID пользователя");
            req.setAttribute("statusCode", 400);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        try {
            Optional<User> currentUserOpt = userService.getById(userId);
            if (currentUserOpt.isEmpty()) {
                req.setAttribute("errorMessage", "Пользователь не найден");
                req.setAttribute("statusCode", 404);
                req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
                return;
            }

            User currentUser = currentUserOpt.get();
            req.setAttribute("user", currentUser);

            User loggedUser = (User) req.getSession().getAttribute("user");
            req.setAttribute("isOwner", loggedUser != null && loggedUser.getId() == currentUser.getId());

            req.getRequestDispatcher("/templates/user_profile.ftl").forward(req, resp);

        } catch (ServiceException e) {
            logger.error("Error loading user profile", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
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
            req.setAttribute("errorMessage", "Error updating profile");
            req.getRequestDispatcher("/templates/user_edit.ftl").forward(req, resp);
        }
    }
}
