package com.racetalk.web.servlet;

import com.racetalk.entity.Note;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/notes/edit")
public class NoteEditServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(NoteEditServlet.class);

    private NoteService noteService;

    @Override
    public void init() {
        noteService = (NoteService) getServletContext().getAttribute("noteService");
        if (noteService == null) {
            throw new IllegalStateException("NoteService is not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int noteId = Integer.parseInt(req.getParameter("noteId"));
            Optional<Note> noteOpt = noteService.getById(noteId);
            if (noteOpt.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/notes");
                return;
            }
            req.setAttribute("note", noteOpt.get());
            req.getRequestDispatcher("/templates/note_edit.ftl").forward(req, resp);
        } catch (ServiceException e) {
            logger.error("Failed to load note for editing", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}

