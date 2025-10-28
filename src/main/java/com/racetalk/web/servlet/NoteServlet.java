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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@WebServlet("/notes")
public class NoteServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(NoteServlet.class);

    private NoteService noteService;
    private UserService userService;

    @Override
    public void init() {
        noteService = (NoteService) getServletContext().getAttribute("noteService");
        userService = (UserService) getServletContext().getAttribute("userService");
        if (noteService == null) {
            throw new IllegalStateException("Services are not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String currentUsername = (String) req.getSession().getAttribute("user");
            Optional<User> currentUserOpt = userService.getByUsername(currentUsername);
            User currentUser = currentUserOpt.get();
            List<Note> notes = noteService.getUserNotes(currentUser);
            req.setAttribute("notes", notes);

            req.getRequestDispatcher("/templates/notes.ftl").forward(req, resp);
        } catch (ServiceException e) {
            logger.error("Failed to load notes", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String currentUsername = (String) req.getSession().getAttribute("user");
            Optional<User> currentUserOpt = userService.getByUsername(currentUsername);
            User currentUser = currentUserOpt.get();

            String action = req.getParameter("action");

            if ("delete".equals(action)) {
                int noteId = Integer.parseInt(req.getParameter("noteId"));
                noteService.deleteNote(noteId);
                resp.sendRedirect(req.getContextPath() + "/notes");
                return;
            }

            String title = req.getParameter("title");
            String content = req.getParameter("content");

            Note note = new Note();
            note.setUser(currentUser);
            note.setTitle(title);
            note.setContent(content);
            note.setCreatedAt(LocalDateTime.now());

            noteService.addNote(note);

            resp.sendRedirect(req.getContextPath() + "/notes");
        } catch (ServiceException e) {
            logger.error("Failed to process note post request", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}