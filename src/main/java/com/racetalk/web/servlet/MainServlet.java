package com.racetalk.web.servlet;

import com.racetalk.entity.Race;
import com.racetalk.service.RaceService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Main", urlPatterns = "/main")
public class MainServlet extends HttpServlet {
    private RaceService raceService;

    @Override
    public void init() {
        raceService = (RaceService) getServletContext().getAttribute("raceService");
        if (raceService == null) {
            throw new IllegalStateException("RaceService not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        String username = (String) session.getAttribute("user");
        req.setAttribute("user", username);

        List<Race> races = raceService.getUpcomingRaces();
        req.setAttribute("races", races);

        req.getRequestDispatcher("/templates/main.ftl").forward(req, resp);
    }
}

