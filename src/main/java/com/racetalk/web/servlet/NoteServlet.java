package com.racetalk.web.servlet;

import com.racetalk.entity.Note;
import com.racetalk.entity.User;
import com.racetalk.service.NoteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/notes")
public class NoteServlet extends HttpServlet {
    private NoteService noteService;

    @Override
    public void init() {
        noteService = (NoteService) getServletContext().getAttribute("noteService");
        if (noteService == null) {
            throw new IllegalStateException("NoteService not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");

        List<Note> notes = noteService.getUserNotes(currentUser);
        req.setAttribute("notes", notes);

        req.getRequestDispatcher("/templates/notes.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");

        String title = req.getParameter("title");
        String content = req.getParameter("content");

        if (title == null) {
            req.setAttribute("noteErrorMessage", "Заголовок не может быть пустым");
            req.getRequestDispatcher("/templates/notes.ftl").forward(req, resp);
        }

        Note note = new Note();
        note.setUser(currentUser);
        note.setTitle(title);
        note.setContent(content);
        note.setCreatedAt(LocalDateTime.now());

        noteService.addNote(note);

        resp.sendRedirect(req.getContextPath() + "/notes");
    }
}