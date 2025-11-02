package com.racetalk.web.servlet;

import com.racetalk.entity.User;
import com.racetalk.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserDelete", urlPatterns = "/user/delete")
public class UserDeleteServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserDeleteServlet.class);
    private UserService userService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
        if (userService == null) throw new IllegalStateException("UserService not initialized");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            User currentUser = (User) req.getSession().getAttribute("user");
            userService.deleteUser(currentUser.getId());
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (Exception e) {
            logger.error("Error deleting user", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}
