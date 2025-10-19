package com.racetalk.dao.impl;

import com.racetalk.dao.RaceDao;
import com.racetalk.entity.Race;
import com.racetalk.entity.Team;
import com.racetalk.util.DatabaseConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RaceDaoImpl implements RaceDao {
    private final DatabaseConnectionUtil databaseConnection;

    public RaceDaoImpl(DatabaseConnectionUtil databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void create(Race race) {
        String sql = "INSERT INTO races (session_key, name, location, race_date, is_finished) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, race.getSessionKey());
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
        String sql = "UPDATE races SET session_key=?, name=?, location=?, race_date=?, is_finished=? WHERE id=?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, race.getSessionKey());
            ps.setString(2, race.getName());
            ps.setString(3, race.getLocation());
            ps.setDate(4, Date.valueOf(race.getRaceDate()));
            ps.setBoolean(5, race.isFinished());
            ps.setInt(6, race.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM races WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
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
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Race race = new Race();
                race.setId(rs.getInt("id"));
                race.setSessionKey(rs.getInt("session_key"));
                race.setName(rs.getString("name"));
                race.setLocation(rs.getString("location"));
                race.setRaceDate(rs.getDate("race_date").toLocalDate());
                race.setFinished(rs.getBoolean("is_finished"));
                races.add(race);
            }
            return races;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Race> findAll() {
        String sql = "SELECT * FROM races";
        List<Race> races = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Race race = new Race();
                race.setId(rs.getInt("id"));
                race.setSessionKey(rs.getInt("session_key"));
                race.setName(rs.getString("name"));
                race.setLocation(rs.getString("location"));
                race.setRaceDate(rs.getDate("race_date").toLocalDate());
                race.setFinished(rs.getBoolean("is_finished"));
                races.add(race);
            }
            return races;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Race> findBySessionKey(int sessionKey) {
        String sql = "SELECT * FROM race WHERE session_key = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, sessionKey);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Race race = new Race();
                race.setId(rs.getInt("id"));
                race.setSessionKey(rs.getInt("session_key"));
                race.setName(rs.getString("name"));
                race.setLocation(rs.getString("location"));
                race.setRaceDate(rs.getDate("race_date").toLocalDate());
                race.setFinished(rs.getBoolean("finished"));
                return Optional.of(race);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Race> findById(int id) {
        String sql = "SELECT * FROM races WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Race race = new Race();
                race.setId(rs.getInt("id"));
                race.setSessionKey(rs.getInt("session_key"));
                race.setName(rs.getString("name"));
                race.setLocation(rs.getString("location"));
                race.setRaceDate(rs.getDate("race_date").toLocalDate());
                race.setFinished(rs.getBoolean("finished"));
                return Optional.of(race);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
