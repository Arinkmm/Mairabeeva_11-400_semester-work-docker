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
    public void createPastRace(Race race) {
        String sql = "INSERT INTO past_races (session_key, location, race_date, is_finished) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, race.getSessionKey());
            ps.setString(2, race.getLocation());
            ps.setDate(3, Date.valueOf(race.getRaceDate()));
            ps.setBoolean(4, race.isFinished());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                race.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void updatePastRace(Race race) {
        String sql = "UPDATE past_races SET session_key=?, location=?, race_date=?, is_finished=? WHERE id=?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, race.getSessionKey());
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
    public boolean existsPastRaceById(int id) {
        String sql = "SELECT 1 FROM past_races WHERE id = ?";
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
        String sql = "SELECT * FROM upcoming_races";
        List<Race> races = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Race race = new Race();
                race.setId(rs.getInt("id"));
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
    public List<Race> findPastRaces() {
        String sql = "SELECT * FROM past_races";
        List<Race> races = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Race race = new Race();
                race.setId(rs.getInt("id"));
                race.setSessionKey(rs.getInt("session_key"));
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
        String sql = "SELECT * FROM past_races WHERE session_key = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, sessionKey);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Race race = new Race();
                race.setId(rs.getInt("id"));
                race.setSessionKey(rs.getInt("session_key"));
                race.setLocation(rs.getString("location"));
                race.setRaceDate(rs.getDate("race_date").toLocalDate());
                race.setFinished(rs.getBoolean("is_finished"));
                return Optional.of(race);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Race> findPastRaceById(int id) {
        String sql = "SELECT * FROM past_races WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Race race = new Race();
                race.setId(rs.getInt("id"));
                race.setSessionKey(rs.getInt("session_key"));
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
