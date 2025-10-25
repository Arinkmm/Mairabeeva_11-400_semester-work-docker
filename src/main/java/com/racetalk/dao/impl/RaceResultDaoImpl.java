package com.racetalk.dao.impl;

import com.racetalk.dao.DriverDao;
import com.racetalk.dao.RaceDao;
import com.racetalk.dao.RaceResultDao;
import com.racetalk.entity.Driver;
import com.racetalk.entity.Race;
import com.racetalk.entity.RaceResult;
import com.racetalk.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RaceResultDaoImpl implements RaceResultDao {
    private final DatabaseConnectionUtil databaseConnection;
    private final RaceDao raceDao;
    private final DriverDao driverDao;

    public RaceResultDaoImpl(DatabaseConnectionUtil databaseConnection, RaceDao raceDao, DriverDao driverDao) {
        this.databaseConnection = databaseConnection;
        this.raceDao = raceDao;
        this.driverDao = driverDao;
    }

    @Override
    public void create(RaceResult result) {
        String sql = "INSERT INTO race_results (race_id, driver_number, position, points) VALUES (?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, result.getRace().getId());
            ps.setInt(2, result.getDriver().getDriverNumber());
            ps.setInt(3, result.getPosition());
            ps.setInt(4, result.getPoints());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(RaceResult result) {
        String sql = "UPDATE race_results SET race_id=?, driver_number=?, position=?, points=? WHERE id=?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, result.getRace().getId());
            ps.setInt(2, result.getDriver().getDriverNumber());
            ps.setInt(3, result.getPosition());
            ps.setInt(4, result.getPoints());
            ps.setInt(5, result.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RaceResult> findResultsByRaceId(int raceId) {
        String sql = "SELECT * FROM race_results WHERE race_id = ? ORDER BY (position = 0), position";
        List<RaceResult> results = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, raceId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                RaceResult result = new RaceResult();
                result.setId(rs.getInt("id"));

                Race race = raceDao.findPastRaceById(raceId).orElse(null);
                int driverNumber = rs.getInt("driver_number");
                Driver driver = driverDao.findByDriverNumber(driverNumber).orElse(null);

                result.setRace(race);
                result.setDriver(driver);
                result.setPosition(rs.getInt("position"));
                result.setPoints(rs.getInt("points"));

                results.add(result);
            }
            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RaceResult> findResultsByDriverNumber(int driverNumber) {
        String sql = "SELECT * FROM race_results WHERE driver_number = ? ORDER BY race_id";
        List<RaceResult> results = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, driverNumber);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                RaceResult result = new RaceResult();
                result.setId(rs.getInt("id"));

                int raceId = rs.getInt("race_id");
                Race race = raceDao.findPastRaceById(raceId).orElse(null);

                Driver driver = driverDao.findByDriverNumber(driverNumber).orElse(null);

                result.setRace(race);
                result.setDriver(driver);
                result.setPosition(rs.getInt("position"));
                result.setPoints(rs.getInt("points"));

                results.add(result);
            }
            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<RaceResult> findResultsByRaceIdAndDriverNumber(int raceId, int driverNumber) {
        String sql = "SELECT * FROM race_results WHERE race_id = ? AND driver_number = ?";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, raceId);
            ps.setInt(2, driverNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                RaceResult result = new RaceResult();
                result.setId(rs.getInt("id"));

                Race race = raceDao.findPastRaceById(raceId).orElse(null);

                Driver driver = driverDao.findByDriverNumber(driverNumber).orElse(null);

                result.setRace(race);
                result.setDriver(driver);
                result.setPosition(rs.getInt("position"));
                result.setPoints(rs.getInt("points"));
                return Optional.of(result);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
