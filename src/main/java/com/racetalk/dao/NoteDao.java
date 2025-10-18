package com.racetalk.dao;

import com.racetalk.entity.Note;
import com.racetalk.entity.User;

import java.util.List;


public interface NoteDao {
    void create(Note note);

    List<Note> findByUser(User user);
}
