package com.racetalk.web.servlet;

import com.racetalk.entity.Race;
import com.racetalk.exception.ServiceException;
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

@WebServlet(name = "Races", urlPatterns = "/races")
public class RaceServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(RaceServlet.class);

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
        try {
            List<Race> races = raceService.getPastRaces();
            req.setAttribute("races", races);

            req.getRequestDispatcher("/templates/races.ftl").forward(req, resp);
        } catch (ServiceException e) {
            logger.error("Failed to load races", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}
