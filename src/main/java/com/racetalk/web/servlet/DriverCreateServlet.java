package com.racetalk.web.servlet;

import com.racetalk.entity.Driver;
import com.racetalk.entity.Team;
import com.racetalk.entity.User;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.DriverService;
import com.racetalk.service.TeamService;
import com.racetalk.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "DriverCreate", urlPatterns = "/driver/create")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class DriverCreateServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DriverCreateServlet.class);

    private DriverService driverService;
    private UserService userService;
    private TeamService teamService;

    @Override
    public void init() {
        driverService = (DriverService) getServletContext().getAttribute("driverService");
        userService = (UserService) getServletContext().getAttribute("userService");
        teamService = (TeamService) getServletContext().getAttribute("teamService");
        if (driverService == null || userService == null || teamService == null) {
            throw new IllegalStateException("Services not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || !userService.isAdmin(user)) {
            req.setAttribute("errorMessage", "Доступ запрещён");
            req.setAttribute("statusCode", 403);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        try {
            List<Team> teams = teamService.getAllTeams();
            req.setAttribute("teams", teams);
            req.getRequestDispatcher("/templates/driver_create.ftl").forward(req, resp);
        } catch (ServiceException e) {
            logger.error("Error to loading driver create", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/templates/driver_create.ftl").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || !userService.isAdmin(user)) {
            req.setAttribute("errorMessage", "Доступ запрещён");
            req.setAttribute("statusCode", 403);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        try {
            int driverNumber = Integer.parseInt(req.getParameter("driverNumber"));
            if (driverNumber < 1) {
                req.setAttribute("DriverCreateErrorMessage", "Неверный формат номера гонщика");
                req.getRequestDispatcher("/templates/driver_create.ftl").forward(req, resp);
                return;
            }
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String country = req.getParameter("country");
            LocalDate dateOfBirth = LocalDate.parse(req.getParameter("dateOfBirth"));
            int teamId = Integer.parseInt(req.getParameter("teamId"));
            Team team = teamService.getById(teamId).get();

            Driver driver = new Driver();
            driver.setDriverNumber(driverNumber);
            driver.setFirstName(firstName);
            driver.setLastName(lastName);
            driver.setCountry(country);
            driver.setDateOfBirth(dateOfBirth);
            driver.setTeam(team);

            Part photoPart = req.getPart("photo");
            InputStream photoInputStream = photoPart.getInputStream();

            driverService.createDriver(driver, photoInputStream);

            resp.sendRedirect(req.getContextPath() + "/driver/" + driverNumber);
        } catch (Exception e) {
            logger.error("Failed to create driver", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/templates/driver_create.ftl").forward(req, resp);
        }
    }
}