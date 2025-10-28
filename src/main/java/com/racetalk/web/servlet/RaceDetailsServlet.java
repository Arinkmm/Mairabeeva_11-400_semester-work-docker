package com.racetalk.web.servlet;

import com.racetalk.entity.Race;
import com.racetalk.entity.RaceResult;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.RaceResultService;
import com.racetalk.service.RaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "RaceDetails", urlPatterns = "/race/*")
public class RaceDetailsServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(RaceDetailsServlet.class);

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
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            req.setAttribute("errorMessage", "ID гонки не найден");
            req.setAttribute("statusCode", 400);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        String raceIdStr = pathInfo.substring(1);
        int raceId;
        try {
            raceId = Integer.parseInt(raceIdStr);
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Неверный формат ID гонки");
            req.setAttribute("statusCode", 400);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        try {
            Optional<Race> raceOpt = raceService.getPastRaceById(raceId);
            if (raceOpt.isEmpty()) {
                req.setAttribute("errorMessage", "Гонка не найдена");
                req.setAttribute("statusCode", 404);
                req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
                return;
            }

            List<RaceResult> results = raceResultService.getResultsByRaceId(raceId);

            Race race = raceOpt.get();

            req.setAttribute("race", race);
            req.setAttribute("results", results);

            req.getRequestDispatcher("/templates/race_details.ftl").forward(req, resp);
        } catch (ServiceException e) {
            logger.error("Error loading race details", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}
