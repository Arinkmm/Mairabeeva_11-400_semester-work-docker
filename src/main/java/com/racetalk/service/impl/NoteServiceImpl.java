package com.racetalk.service.impl;

import com.racetalk.dao.NoteDao;
import com.racetalk.entity.Note;
import com.racetalk.entity.User;
import com.racetalk.exception.DataAccessException;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NoteServiceImpl implements NoteService {
    private static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);
    private final NoteDao noteDao;

    public NoteServiceImpl(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    public void addNote(Note note) {
        try {
            noteDao.create(note);
        } catch (DataAccessException e) {
            logger.error("Failed to add note for user {}", note.getUser().getId(), e);
            throw new ServiceException("Failed to add note", e);
        }
    }

    @Override
    public List<Note> getUserNotes(User user) {
        try {
            return noteDao.findByUser(user);
        } catch (DataAccessException e) {
            logger.error("Failed to get notes for user {}", user.getId(), e);
            throw new ServiceException("Failed to get user notes", e);
        }
    }

    @Override
    public void deleteNote(int id) {
        try {
            noteDao.deleteById(id);
        } catch (DataAccessException e) {
            logger.error("Failed to delete note with id {}", id, e);
            throw new ServiceException("Failed to delete note", e);
        }
    }
}
