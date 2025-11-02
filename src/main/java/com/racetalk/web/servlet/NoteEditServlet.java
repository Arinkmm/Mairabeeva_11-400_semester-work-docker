package com.racetalk.web.servlet;

import com.racetalk.entity.Note;
import com.racetalk.entity.User;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.NoteService;
import com.racetalk.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "NoteEdit", urlPatterns = "/notes/edit/*")
public class NoteEditServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(NoteEditServlet.class);

    private NoteService noteService;
    private UserService userService;

    @Override
    public void init() {
        noteService = (NoteService) getServletContext().getAttribute("noteService");
        userService = (UserService) getServletContext().getAttribute("userService");
        if (noteService == null || userService == null) {
            throw new IllegalStateException("Services are not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            req.setAttribute("errorMessage", "ID заметки не найден");
            req.setAttribute("statusCode", 400);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }
        String noteIdStr = pathInfo.substring(1);
        int noteId;
        try {
            noteId = Integer.parseInt(noteIdStr);
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Неверный формат ID заметки");
            req.setAttribute("statusCode", 400);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }
        try {
            Optional<Note> noteOpt = noteService.getById(noteId);
            if (noteOpt.isEmpty()) {
                req.setAttribute("errorMessage", "Заметка не найдена");
                req.setAttribute("statusCode", 404);
                req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
                return;
            }

            Note note = noteOpt.get();
            req.setAttribute("note", note);

            User currentUser = (User) req.getSession().getAttribute("user");

            if (userService.isAdmin(currentUser) || note.getUser().getId() != currentUser.getId()) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            req.getRequestDispatcher("/templates/note_edit.ftl").forward(req, resp);
        } catch (ServiceException e) {
            logger.error("Failed to load note for editing", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            req.setAttribute("errorMessage", "ID заметки не найден");
            req.setAttribute("statusCode", 400);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }
        String noteIdStr = pathInfo.substring(1);
        int noteId;
        try {
            noteId = Integer.parseInt(noteIdStr);
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Неверный формат ID заметки");
            req.setAttribute("statusCode", 400);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        String title = req.getParameter("title");
        String content = req.getParameter("content");

        try {
            Optional<Note> noteOpt = noteService.getById(noteId);
            if (noteOpt.isEmpty()) {
                req.setAttribute("errorMessage", "Заметка не найдена");
                req.setAttribute("statusCode", 404);
                req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
                return;
            }

            Note note = noteOpt.get();
            req.setAttribute("note", note);

            User currentUser = (User) req.getSession().getAttribute("user");

            if (userService.isAdmin(currentUser) || note.getUser().getId() != currentUser.getId()) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            note.setTitle(title);
            note.setContent(content);
            noteService.editNote(note);
            resp.sendRedirect(req.getContextPath() + "/notes");
        } catch (Exception e) {
            logger.error("Failed to edit note", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}

