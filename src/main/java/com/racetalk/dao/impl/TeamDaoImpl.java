package com.racetalk.dao.impl;

import com.racetalk.dao.TeamDao;
import com.racetalk.entity.Team;
import com.racetalk.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeamDaoImpl implements TeamDao {
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
            ps.setInt(3, team.getFoundedYear());
            ps.setString(4, team.getPhoto());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                team.setFoundedYear(rs.getInt("founded_year"));
                team.setPhoto(rs.getString("photo"));
                return Optional.of(team);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                team.setFoundedYear(rs.getInt("founded_year"));
                team.setPhoto(rs.getString("photo"));
                teams.add(team);
            }
            return teams;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

