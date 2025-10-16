package com.racetalk.dao.impl;

import com.racetalk.dao.MessageDao;
import com.racetalk.dao.UserDao;
import com.racetalk.entity.Message;
import com.racetalk.entity.User;
import com.racetalk.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDaoImpl implements MessageDao {
    private final Connection connection = DatabaseConnectionUtil.getConnection();
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public void create(Message message) {
        String sql = "INSERT INTO messages (user_id, content, created_at) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (message.getUser() != null) {
                ps.setInt(1, message.getUser().getId());
            } else {
                ps.setNull(1, Types.INTEGER);
            }

            ps.setString(2, message.getContent());
            ps.setTimestamp(3, Timestamp.valueOf(message.getCreatedAt()));
            ps.executeUpdate();
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Message> findAll() {
        String sql = "SELECT * FROM messages ORDER BY created_at ASC";
        List<Message> messages = new ArrayList<>();
        try {
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getInt("id"));
                int userId = rs.getInt("user_id");
                if (!rs.wasNull()) {
                    User user = userDao.findById(userId).orElse(null);
                    message.setUser(user);
                }
                message.setContent(rs.getString("content"));
                message.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                messages.add(message);
            }
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
