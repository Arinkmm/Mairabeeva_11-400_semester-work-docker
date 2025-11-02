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

@WebServlet(name = "UserProfile", urlPatterns = "/user/profile/*")
public class UserProfileServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserProfileServlet.class);
    private UserService userService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
        if (userService == null) throw new IllegalStateException("UserService not initialized");
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

        int userId;
        try {
            userId = Integer.parseInt(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Неверный формат ID пользователя");
            req.setAttribute("statusCode", 400);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        try {
            Optional<User> userOpt = userService.getById(userId);
            if (userOpt.isEmpty()) {
                req.setAttribute("errorMessage", "Пользователь не найден");
                req.setAttribute("statusCode", 404);
                req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
                return;
            }

            User user = userOpt.get();
            req.setAttribute("user", user);

            User loggedUser = (User) req.getSession().getAttribute("user");
            boolean isOwner = loggedUser.getId() == user.getId();
            boolean canDelete = userService.isAdmin(loggedUser) || isOwner;
            boolean canEdit = isOwner;

            req.setAttribute("isOwner", isOwner);
            req.setAttribute("canDelete", canDelete);
            req.setAttribute("canEdit", canEdit);

            req.getRequestDispatcher("/templates/user_profile.ftl").forward(req, resp);
        } catch (ServiceException e) {
            logger.error("Error loading user profile", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}