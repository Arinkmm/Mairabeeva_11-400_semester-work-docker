package com.racetalk.service;

import com.racetalk.entity.Note;
import com.racetalk.entity.User;

import java.util.List;

public interface NoteService {
    public void addNote(Note note);

    public List<Note> getUserNotes(User user);
}
