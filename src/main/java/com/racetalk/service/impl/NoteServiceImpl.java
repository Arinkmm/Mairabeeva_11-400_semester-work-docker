package com.racetalk.service.impl;

import com.racetalk.dao.NoteDao;
import com.racetalk.entity.Note;
import com.racetalk.entity.User;
import com.racetalk.service.NoteService;

import java.util.List;

public class NoteServiceImpl implements NoteService {
    private final NoteDao noteDao;

    public NoteServiceImpl(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    public void addNote(Note note) {
        noteDao.create(note);
    }

    @Override
    public List<Note> getUserNotes(User user) {
        return noteDao.findByUser(user);
    }

    @Override
    public void deleteNote(int id) {
        noteDao.deleteById(id);
    }
}
