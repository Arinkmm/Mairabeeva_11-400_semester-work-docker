package com.racetalk.dao.impl;

import com.racetalk.dao.TeamDao;
import com.racetalk.entity.Team;
import com.racetalk.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeamDaoImpl implements TeamDao {
    private final Connection connection = DatabaseConnectionUtil.getConnection();

    @Override
    public void create(Team team) {
        String sql = "INSERT INTO teams (name, country, founded_year) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, team.getName());
            ps.setString(2, team.getCountry());
            ps.setInt(3, team.getFoundedYear());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Team> findById(int id) {
        String sql = "SELECT * FROM teams WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Team team = new Team();
                team.setId(rs.getInt("id"));
                team.setName(rs.getString("name"));
                team.setCountry(rs.getString("country"));
                team.setFoundedYear(rs.getInt("founded_year"));
                return Optional.of(team);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Team> findAll() {
        String sql = "SELECT * FROM teams";
        List<Team> teams = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Team team = new Team();
                team.setId(rs.getInt("id"));
                team.setName(rs.getString("name"));
                team.setCountry(rs.getString("country"));
                team.setFoundedYear(rs.getInt("founded_year"));
                teams.add(team);
            }
            return teams;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

