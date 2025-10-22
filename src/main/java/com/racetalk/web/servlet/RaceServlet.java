package com.racetalk.web.servlet;

import com.racetalk.entity.Race;
import com.racetalk.service.RaceService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Races", urlPatterns = "/races")
public class RaceServlet extends HttpServlet {
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

        List<Race> races = raceService.getPastRaces();
        req.setAttribute("races", races);

        req.getRequestDispatcher("/templates/races.ftl").forward(req, resp);
    }
}
