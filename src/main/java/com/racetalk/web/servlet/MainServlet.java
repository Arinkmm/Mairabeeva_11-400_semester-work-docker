package com.racetalk.web.servlet;

import com.racetalk.dao.impl.RaceDaoImpl;
import com.racetalk.entity.Race;
import com.racetalk.entity.User;
import com.racetalk.service.RaceService;
import com.racetalk.service.impl.RaceServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Main", urlPatterns = "/main")
public class MainServlet extends HttpServlet {
    private RaceService raceService = new RaceServiceImpl(new RaceDaoImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/index");
        }

        User user = (User) session.getAttribute("user");
        req.setAttribute("user", user);

        List<Race> races = raceService.getUpcomingRaces();
        req.setAttribute("races", races);

        String cookieUser = "";
        String sessionId = session.getId();
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("user".equalsIgnoreCase(c.getName())) {
                    cookieUser = c.getValue();
                } else if ("jsessionid".equalsIgnoreCase(c.getName())) {
                    sessionId = c.getValue();
                }
            }
        }

        req.setAttribute("cookieUser", cookieUser);
        req.setAttribute("sessionId", sessionId);

        req.getRequestDispatcher("/templates/main.ftl").forward(req, resp);
    }
}

