package com.racetalk.web.servlet;

import com.racetalk.entity.Team;
import com.racetalk.service.TeamService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "TeamDetails", urlPatterns = "/team/details")
public class TeamDetailsServlet extends HttpServlet {
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
        String idInput = req.getParameter("id");
        if (idInput == null || idInput.trim().isEmpty()) {
            req.setAttribute("errorMessage", "Team ID is missing");
            req.setAttribute("errorCode", 400);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        int teamId;
        try {
            teamId = Integer.parseInt(idInput.trim());
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Invalid Team ID format");
            req.setAttribute("errorCode", 400);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        Optional<Team> teamOptional = teamService.getById(teamId);
        if (teamOptional.isEmpty()) {
            req.setAttribute("errorMessage", "Team not found");
            req.setAttribute("errorCode", 404);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        Team team = teamOptional.get();

        req.setAttribute("team", team);

        req.getRequestDispatcher("/templates/team_details.ftl").forward(req, resp);
    }
}
