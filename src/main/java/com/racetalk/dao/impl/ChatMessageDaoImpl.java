package com.racetalk.dao.impl;

import com.racetalk.dao.ChatMessageDao;
import com.racetalk.dao.UserDao;
import com.racetalk.entity.ChatMessage;
import com.racetalk.entity.User;
import com.racetalk.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageDaoImpl implements ChatMessageDao {
    private final DatabaseConnectionUtil databaseConnection;
    private final UserDao userDao;

    public ChatMessageDaoImpl(DatabaseConnectionUtil databaseConnection, UserDao userDao) {
        this.databaseConnection = databaseConnection;
        this.userDao = userDao;
    }

    @Override
    public void create(ChatMessage chatMessage) {
        String sql = "INSERT INTO messages (user_id, content, created_at) VALUES (?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            if (chatMessage.getUser() != null) {
                ps.setInt(1, chatMessage.getUser().getId());
            } else {
                ps.setNull(1, Types.INTEGER);
            }

            ps.setString(2, chatMessage.getContent());
            ps.setTimestamp(3, Timestamp.valueOf(chatMessage.getCreatedAt()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ChatMessage> findAll() {
        String sql = "SELECT * FROM messages ORDER BY created_at ASC";
        List<ChatMessage> chatMessages = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(rs.getInt("id"));
                int userId = rs.getInt("user_id");
                User user = userDao.findById(userId).orElse(null);
                chatMessage.setUser(user);
                chatMessage.setContent(rs.getString("content"));
                chatMessage.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                chatMessages.add(chatMessage);
            }
            return chatMessages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}