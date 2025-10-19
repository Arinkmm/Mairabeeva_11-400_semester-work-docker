package com.racetalk.web.servlet;

import com.racetalk.entity.Driver;
import com.racetalk.service.DriverService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "DriverProfile", urlPatterns = "/driver/profile")
public class DriverProfileServlet extends HttpServlet {
    private DriverService driverService;

    @Override
    public void init() {
        driverService = (DriverService) getServletContext().getAttribute("driverService");
        if (driverService == null) {
            throw new IllegalStateException("DriverService not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idInput = req.getParameter("id");
        if (idInput == null) {
            req.setAttribute("errorMessage", "Driver ID is missing");
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
        }

        int driverId = Integer.parseInt(idInput);
        Optional<Driver> driverOptional = driverService.getById(driverId);
        if (driverOptional.isEmpty()) {
            req.setAttribute("errorMessage", "Driver not found");
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
        }

        Driver driver = driverOptional.get();

        req.setAttribute("driver", driver);

        req.getRequestDispatcher("/templates/driver_profile.ftl").forward(req, resp);
    }
}
