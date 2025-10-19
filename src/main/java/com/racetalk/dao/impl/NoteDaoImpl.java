package com.racetalk.dao.impl;

import com.racetalk.dao.NoteDao;
import com.racetalk.dao.TeamDao;
import com.racetalk.entity.Note;
import com.racetalk.entity.User;
import com.racetalk.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDaoImpl implements NoteDao {
    private final DatabaseConnectionUtil databaseConnection;

    public NoteDaoImpl(DatabaseConnectionUtil databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void create(Note note) {
        String sql = "INSERT INTO notes (user_id, title, content, created_at) VALUES (?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, note.getUser().getId());
            ps.setString(2, note.getTitle());
            ps.setString(3, note.getContent());
            ps.setTimestamp(4, Timestamp.valueOf(note.getCreatedAt()));
            ps.executeUpdate();
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Note> findByUser(User user) {
        String sql = "SELECT * FROM notes WHERE user_id = ? ORDER BY created_at DESC";
        List<Note> notes = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                    Note note = new Note();
                    note.setId(rs.getInt("id"));
                    note.setUser(user);
                    note.setTitle(rs.getString("title"));
                    note.setContent(rs.getString("content"));
                    note.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    notes.add(note);
                }
            return notes;
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
