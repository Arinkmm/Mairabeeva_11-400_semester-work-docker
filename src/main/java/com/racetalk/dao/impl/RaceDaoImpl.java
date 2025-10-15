package com.racetalk.dao.impl;

import com.racetalk.dao.RaceDao;
import com.racetalk.entity.Race;
import com.racetalk.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RaceDaoImpl implements RaceDao {
    private final Connection connection = DatabaseConnectionUtil.getConnection();

    @Override
    public void create(Race race) {
        String sql = "INSERT INTO races (id, name, location, race_date, is_finished) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, race.getId());
            ps.setString(2, race.getName());
            ps.setString(3, race.getLocation());
            ps.setDate(4, Date.valueOf(race.getRaceDate()));
            ps.setBoolean(5, race.isFinished());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Race race) {
        String sql = "UPDATE races SET name=?, location=?, race_date=?, is_finished=? WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, race.getName());
            ps.setString(2, race.getLocation());
            ps.setDate(3, Date.valueOf(race.getRaceDate()));
            ps.setBoolean(4, race.isFinished());
            ps.setInt(5, race.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM races WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Race> findUpcomingRaces() {
        String sql = "SELECT * FROM races WHERE race_date >= CURRENT_DATE ORDER BY race_date";
        List<Race> races = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Race race = new Race();
                race.setId(rs.getInt("id"));
                race.setName(rs.getString("name"));
                race.setLocation(rs.getString("location"));
                race.setRaceDate(rs.getDate("race_date").toLocalDate());
                race.setFinished(rs.getBoolean("is_finished"));
                races.add(race);
            }
            return races;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
