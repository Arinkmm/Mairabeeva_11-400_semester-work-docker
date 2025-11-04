package com.racetalk.dao;

import com.racetalk.entity.Note;
import com.racetalk.entity.User;

import java.util.List;
import java.util.Optional;


public interface NoteDao {
    void create(Note note);

    void update(Note note);

    void deleteById(int id);

    List<Note> findByUser(User user);

    Optional<Note> findById(int id);
}
