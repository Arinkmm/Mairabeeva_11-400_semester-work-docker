package com.racetalk.web.servlet;

import com.racetalk.entity.Team;
import com.racetalk.exception.ServiceException;
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

@WebServlet(name = "Teams", urlPatterns = "/teams")
public class TeamServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TeamServlet.class);

    private TeamService teamService;

    @Override
    public void init() {
        teamService = (TeamService) getServletContext().getAttribute("teamService");
        if (teamService == null) {
            throw new IllegalStateException("TeamService not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Team> teams = teamService.getAllTeams();
            req.setAttribute("teams", teams);

            req.getRequestDispatcher("/templates/teams.ftl").forward(req, resp);
        } catch (ServiceException e) {
            logger.error("Failed to load teams list", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}
