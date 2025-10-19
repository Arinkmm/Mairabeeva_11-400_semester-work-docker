package com.racetalk.web.servlet;

import com.racetalk.entity.Team;
import com.racetalk.service.TeamService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Teams", urlPatterns = "/teams")
public class TeamServlet extends HttpServlet {
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

        List<Team> teams = teamService.getAllTeams();
        req.setAttribute("teams", teams);

        req.getRequestDispatcher("/templates/teams.ftl").forward(req, resp);
    }
}
