package com.racetalk.web.servlet;

import com.racetalk.entity.Note;
import com.racetalk.entity.User;
import com.racetalk.service.NoteService;
import com.racetalk.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "NoteDelete", urlPatterns = "/notes/delete/*")
public class NoteDeleteServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(NoteDeleteServlet.class);

    private NoteService noteService;
    private UserService userService;

    @Override
    public void init() {
        noteService = (NoteService) getServletContext().getAttribute("noteService");
        userService = (UserService) getServletContext().getAttribute("userService");
        if (noteService == null || userService == null) {
            throw new IllegalStateException("Services not initialized");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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

            User currentUser = (User) req.getSession().getAttribute("user");

            if (userService.isAdmin(currentUser) || note.getUser().getId() != currentUser.getId()) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            noteService.deleteNote(noteId);
            resp.sendRedirect(req.getContextPath() + "/notes");
        } catch (Exception e) {
            logger.error("Failed to delete note", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}
