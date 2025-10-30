package com.racetalk.service;

import com.racetalk.entity.Note;
import com.racetalk.entity.User;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    void addNote(Note note);

    void editNote(Note note);

    void deleteNote(int id);

    List<Note> getUserNotes(User user);

    Optional<Note> getById(int id);}
