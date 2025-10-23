package com.racetalk.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/error")
public class ErrorHandler extends HttpServlet {
    private static final Map<Integer, String> ERROR_MESSAGES = new HashMap<>();

    static {
        ERROR_MESSAGES.put(400, "Неверный запрос");
        ERROR_MESSAGES.put(401, "Доступ запрещён");
        ERROR_MESSAGES.put(403, "Доступ запрещён");
        ERROR_MESSAGES.put(404, "Страница не найдена");
        ERROR_MESSAGES.put(500, "Внутренняя ошибка сервера");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");

        if (statusCode == null) {
            statusCode = 500;
        }

        String message = ERROR_MESSAGES.get(statusCode);

        req.setAttribute("statusCode", statusCode);
        req.setAttribute("errorMessage", message);

        resp.setStatus(statusCode);
        req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
    }
}
