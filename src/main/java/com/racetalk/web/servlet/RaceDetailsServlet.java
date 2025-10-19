package com.racetalk.web.servlet;

import com.racetalk.entity.Race;
import com.racetalk.entity.RaceResult;
import com.racetalk.service.RaceResultService;
import com.racetalk.service.RaceService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "RaceDetails", urlPatterns = "/race/details")
public class RaceDetailsServlet extends HttpServlet {
    private RaceService raceService;
    private RaceResultService raceResultService;

    @Override
    public void init() {
        raceService = (RaceService) getServletContext().getAttribute("raceService");
        raceResultService = (RaceResultService) getServletContext().getAttribute("raceResultService");
        if (raceService == null || raceResultService == null) {
            throw new IllegalStateException("Services are not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idInput = req.getParameter("id");
        if (idInput == null) {
            req.setAttribute("errorMessage", "Race ID is missing");
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
        }

        int raceId = Integer.parseInt(idInput);
        Optional<Race> raceOptional = raceService.getById(raceId);
        if (raceOptional.isEmpty()) {
            req.setAttribute("errorMessage", "Race not found");
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
        }

        List<RaceResult> results = raceResultService.getResultsByRaceId(raceId);

        Race race = raceOptional.get();

        req.setAttribute("race", race);
        req.setAttribute("results", results);

        req.getRequestDispatcher("/templates/race_details.ftl").forward(req, resp);
    }
}
