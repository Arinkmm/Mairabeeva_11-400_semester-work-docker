package com.racetalk.dao;

import com.racetalk.entity.Note;
import com.racetalk.entity.User;

import java.util.List;


public interface NoteDao {
    public void create(Note note);

    public List<Note> findByUser(User user);
}
