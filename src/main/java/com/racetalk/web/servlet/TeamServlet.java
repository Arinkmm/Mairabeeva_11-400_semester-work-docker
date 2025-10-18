package com.racetalk.web.servlet;

import com.racetalk.dao.impl.TeamDaoImpl;
import com.racetalk.entity.Team;
import com.racetalk.service.TeamService;
import com.racetalk.service.impl.TeamServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Teams", urlPatterns = "/teams")
public class TeamServlet extends HttpServlet {
    private TeamService teamService = new TeamServiceImpl(new TeamDaoImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Team> teams = teamService.getAllTeams();
        req.setAttribute("teams", teams);

        req.getRequestDispatcher("/templates/teams.ftl").forward(req, resp);
    }
}
