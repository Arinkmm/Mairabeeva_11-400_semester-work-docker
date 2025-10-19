package com.racetalk.web.servlet;

import com.racetalk.entity.Driver;
import com.racetalk.service.DriverService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Drivers", urlPatterns = "/drivers")
public class DriverServlet extends HttpServlet {
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

        List<Driver> drivers = driverService.getAllDrivers();
        req.setAttribute("drivers", drivers);

        req.getRequestDispatcher("/templates/drivers.ftl").forward(req, resp);
    }
}
