package com.racetalk.web.servlet;

import com.racetalk.entity.Race;
import com.racetalk.entity.User;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.RaceService;
import com.racetalk.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Main", urlPatterns = "/main")
public class MainServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(MainServlet.class);

    private RaceService raceService;
    private UserService userService;

    @Override
    public void init() {
        raceService = (RaceService) getServletContext().getAttribute("raceService");
        userService = (UserService) getServletContext().getAttribute("userService");
        if (raceService == null || userService == null) {
            throw new IllegalStateException("Services are not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(false);
            User user = (User) session.getAttribute("user");
            req.setAttribute("user", user);

            boolean isAdmin = userService.isAdmin(user);
            req.setAttribute("isAdmin", isAdmin);

            List<Race> races = raceService.getUpcomingRaces();
            req.setAttribute("races", races);

            req.getRequestDispatcher("/templates/main.ftl").forward(req, resp);
        } catch (ServiceException e) {
            logger.error("Failed to load main page races", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}

