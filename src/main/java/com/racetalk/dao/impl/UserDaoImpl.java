package com.racetalk.dao.impl;

import com.racetalk.dao.UserDao;
import com.racetalk.entity.User;
import com.racetalk.entity.UserRole;
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
        String sql = "INSERT INTO users (username, password, status, photo, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getStatus());
            ps.setString(4, user.getPhoto());
            ps.setString(5, user.getRole().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error creating user with username {}", user.getUsername(), e);
            throw new DataAccessException("Failed to create user", e);
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET username = ?, password = ?, status = ?, photo = ?, role = ? WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getStatus());
            ps.setString(4, user.getPhoto());
            ps.setString(5, user.getRole().name());
            ps.setInt(6, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error updating user for id {}", user.getId(), e);
            throw new DataAccessException("Failed to update user", e);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting user id {}", id, e);
            throw new DataAccessException("Failed to delete user by id", e);
        }
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
                user.setStatus(rs.getString("status"));
                user.setPhoto(rs.getString("photo"));
                String role = rs.getString("role");
                user.setRole(role != null ? UserRole.valueOf(role) : UserRole.USER);
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
                user.setStatus(rs.getString("status"));
                user.setPhoto(rs.getString("photo"));
                String role = rs.getString("role");
                user.setRole(role != null ? UserRole.valueOf(role) : UserRole.USER);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            logger.error("Error finding user by id {}", id, e);
            throw new DataAccessException("Failed to find user by id", e);
        }
    }
}