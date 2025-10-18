package com.racetalk.service;

import com.racetalk.entity.Note;
import com.racetalk.entity.User;

import java.util.List;

public interface NoteService {
    void addNote(Note note);

    List<Note> getUserNotes(User user);
}
