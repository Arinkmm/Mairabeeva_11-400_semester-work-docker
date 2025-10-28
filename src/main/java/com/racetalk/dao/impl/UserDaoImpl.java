package com.racetalk.dao.impl;

import com.racetalk.dao.UserDao;
import com.racetalk.entity.User;
import com.racetalk.exception.DataAccessException;
import com.racetalk.util.DatabaseConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    private final DatabaseConnectionUtil databaseConnection;

    public UserDaoImpl(DatabaseConnectionUtil databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void create(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error creating user with username {}", user.getUsername(), e);
            throw new DataAccessException("Failed to create user", e);        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            logger.error("Error finding user by username {}", username, e);
            throw new DataAccessException("Failed to find user by username", e);
        }
    }

    @Override
    public Optional<User> findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            logger.error("Error finding user by id {}", id, e);
            throw new DataAccessException("Failed to find user by id", e);
        }
    }
}
