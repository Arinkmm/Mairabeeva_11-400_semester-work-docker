package com.racetalk.dao.impl;

import com.racetalk.dao.TeamDao;
import com.racetalk.entity.Team;
import com.racetalk.exception.DataAccessException;
import com.racetalk.util.DatabaseConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeamDaoImpl implements TeamDao {
    private static final Logger logger = LoggerFactory.getLogger(TeamDaoImpl.class);
    private final DatabaseConnectionUtil databaseConnection;

    public TeamDaoImpl(DatabaseConnectionUtil databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void create(Team team) {
        String sql = "INSERT INTO teams (name, country, founded_year, photo) VALUES (?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, team.getName());
            ps.setString(2, team.getCountry());
            ps.setString(4, team.getPhoto());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error creating team: {}", team, e);
            throw new DataAccessException("Failed to create team", e);
        }
    }

    @Override
    public Optional<Team> findById(int id) {
        String sql = "SELECT * FROM teams WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Team team = new Team();
                team.setId(rs.getInt("id"));
                team.setName(rs.getString("name"));
                team.setCountry(rs.getString("country"));
                if (!rs.wasNull()) {
                    team.setPhoto(rs.getString("photo"));
                } else {
                    team.setPhoto(null);
                }
                return Optional.of(team);
            }
            return Optional.empty();
        } catch (SQLException e) {
            logger.error("Error finding team by id {}", id, e);
            throw new DataAccessException("Failed to find team by id", e);
        }
    }

    @Override
    public List<Team> findAll() {
        String sql = "SELECT * FROM teams ORDER BY id";
        List<Team> teams = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Team team = new Team();
                team.setId(rs.getInt("id"));
                team.setName(rs.getString("name"));
                team.setCountry(rs.getString("country"));
                if (!rs.wasNull()) {
                    team.setPhoto(rs.getString("photo"));
                } else {
                    team.setPhoto(null);
                }
                teams.add(team);
            }
            return teams;
        } catch (SQLException e) {
            logger.error("Error retrieving all teams", e);
            throw new DataAccessException("Failed to retrieve teams", e);
        }
    }
}

