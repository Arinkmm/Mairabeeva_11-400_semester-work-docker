package com.racetalk.dao.impl;

import com.racetalk.dao.DriverDao;
import com.racetalk.dao.RaceDao;
import com.racetalk.dao.RaceResultDao;
import com.racetalk.dao.TeamDao;
import com.racetalk.entity.Driver;
import com.racetalk.entity.Race;
import com.racetalk.entity.RaceResult;
import com.racetalk.entity.Team;
import com.racetalk.exception.DataAccessException;
import com.racetalk.util.DatabaseConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RaceResultDaoImpl implements RaceResultDao {
    private static final Logger logger = LoggerFactory.getLogger(RaceResultDaoImpl.class);
    private final DatabaseConnectionUtil databaseConnection;
    private final RaceDao raceDao;
    private final DriverDao driverDao;
    private final TeamDao teamDao;

    public RaceResultDaoImpl(DatabaseConnectionUtil databaseConnection, RaceDao raceDao, DriverDao driverDao, TeamDao teamDao) {
        this.databaseConnection = databaseConnection;
        this.raceDao = raceDao;
        this.driverDao = driverDao;
        this.teamDao = teamDao;
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
            logger.error("Error creating RaceResult for driver {} in race {}", result.getDriver().getDriverNumber(), result.getRace().getId(), e);
            throw new DataAccessException("Failed to create RaceResult", e);
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
            logger.error("Error updating RaceResult id {}", result.getId(), e);
            throw new DataAccessException("Failed to update RaceResult", e);
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
            logger.error("Error finding results by raceId {}", raceId, e);
            throw new DataAccessException("Failed to find results by raceId", e);
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
            logger.error("Error finding results by driverNumber {}", driverNumber, e);
            throw new DataAccessException("Failed to find results by driverNumber", e);
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
            logger.error("Error finding result by raceId {} and driverNumber {}", raceId, driverNumber, e);
            throw new DataAccessException("Failed to find result by raceId and driverNumber", e);
        }
    }

    @Override
    public List<RaceResult> findResultsByTeamId(int teamId) {
        String sql = "SELECT rr.id, rr.position, rr.points, rr.race_id, rr.driver_number, r.location, r.race_date, r.is_finished, d.driver_number, d.first_name, d.last_name, d.date_of_birth, d.country, d.photo  FROM race_results rr JOIN drivers d ON rr.driver_number = d.driver_number JOIN past_races r ON rr.race_id = r.id WHERE d.team_id = ? ORDER BY rr.race_id, rr.position";

        List<RaceResult> results = new ArrayList<>();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RaceResult raceResult = new RaceResult();
                raceResult.setId(rs.getInt("id"));
                raceResult.setPosition(rs.getInt("position"));
                raceResult.setPoints(rs.getInt("points"));

                Race race = new Race();
                race.setId(rs.getInt("race_id"));
                race.setLocation(rs.getString("location"));
                race.setRaceDate(rs.getDate("race_date").toLocalDate());
                race.setFinished(rs.getBoolean("is_finished"));
                raceResult.setRace(race);

                Driver driver = new Driver();
                driver.setDriverNumber(rs.getInt("driver_number"));

                Team team = teamDao.findById(teamId).orElse(null);
                driver.setTeam(team);

                driver.setFirstName(rs.getString("first_name"));
                driver.setLastName(rs.getString("last_name"));
                driver.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                driver.setCountry(rs.getString("country"));
                if (!rs.wasNull()) {
                    driver.setPhoto(rs.getString("photo"));
                } else {
                    driver.setPhoto(null);
                }
                raceResult.setDriver(driver);

                results.add(raceResult);
            }
            return results;
        } catch (SQLException e) {
            logger.error("Error finding results by teamId {}", teamId, e);
            throw new DataAccessException("Failed to find results by teamId", e);
        }
    }
}
