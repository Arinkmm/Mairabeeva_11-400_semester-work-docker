package com.racetalk.web.servlet;

import com.racetalk.entity.Driver;
import com.racetalk.entity.Race;
import com.racetalk.entity.RaceResult;
import com.racetalk.entity.Team;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.DriverService;
import com.racetalk.service.RaceResultService;
import com.racetalk.service.RaceService;
import com.racetalk.service.TeamService;
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

@WebServlet(name = "TeamDetails", urlPatterns = "/team/*")
public class TeamDetailsServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TeamDetailsServlet.class);

    private TeamService teamService;
    private RaceService raceService;
    private DriverService driverService;
    private RaceResultService raceResultService;

    @Override
    public void init() {
        teamService = (TeamService) getServletContext().getAttribute("teamService");
        raceService = (RaceService) getServletContext().getAttribute("raceService");
        driverService = (DriverService) getServletContext().getAttribute("driverService");
        raceResultService = (RaceResultService) getServletContext().getAttribute("raceResultService");
        if (teamService == null || raceService == null || driverService == null || raceResultService == null) {
            throw new IllegalStateException("Services are not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null) {
                req.setAttribute("errorMessage", "ID команды не найдено");
                req.setAttribute("statusCode", 400);
                req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
                return;
            }

            String teamIdStr = pathInfo.substring(1);
            int teamId;
            try {
                teamId = Integer.parseInt(teamIdStr);
            } catch (NumberFormatException e) {
                req.setAttribute("errorMessage", "Неверный формат ID команды");
                req.setAttribute("statusCode", 400);
                req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
                return;
            }

            Optional<Team> teamOptional = teamService.getById(teamId);
            if (teamOptional.isEmpty()) {
                req.setAttribute("errorMessage", "Команда не найдена");
                req.setAttribute("statusCode", 404);
                req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
                return;
            }

            Team team = teamOptional.get();
            req.setAttribute("team", team);

            List<Race> races = raceService.getPastRaces();
            req.setAttribute("races", races);

            List<RaceResult> results = raceResultService.getResultsByTeamId(teamId);
            req.setAttribute("results", results);

            List<Driver> drivers = driverService.getDriversByTeamId(teamId);
            req.setAttribute("drivers", drivers);

            req.getRequestDispatcher("/templates/team_details.ftl").forward(req, resp);
        } catch (ServiceException e) {
            logger.error("Error loading team details for ID {}", req.getPathInfo(), e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}
