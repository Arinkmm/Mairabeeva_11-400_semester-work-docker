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

public class RaceResultDaoImpl implements RaceResultDao {
    private final Connection connection = DatabaseConnectionUtil.getConnection();
    private final RaceDao raceDao = new RaceDaoImpl();
    private final DriverDao driverDao = new DriverDaoImpl();

    @Override
    public void create(RaceResult result) {
        String sql = "INSERT INTO race_results (id, race_id, driver_id, position, points) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, result.getId());
            ps.setInt(2, result.getRace().getId());
            ps.setInt(3, result.getDriver().getId());
            ps.setInt(4, result.getPosition());
            ps.setInt(5, result.getPoints());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(RaceResult result) {
        String sql = "UPDATE race_results SET race_id=?, driver_id=?, position=?, points=? WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, result.getRace().getId());
            ps.setInt(2, result.getDriver().getId());
            ps.setInt(3, result.getPosition());
            ps.setInt(4, result.getPoints());
            ps.setInt(5, result.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM race_results WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<RaceResult> getResultsByRaceId(int raceId) {
        String sql = "SELECT * FROM race_results WHERE race_id = ? ORDER BY position";
        List<RaceResult> results = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, raceId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RaceResult result = new RaceResult();
                result.setId(rs.getInt("id"));
                Race race = raceDao.findUpcomingRaces().stream()
                        .filter(r -> r.getId() == raceId)
                        .findFirst()
                        .orElse(null);

                Driver driver = driverDao.findById(rs.getInt("driver_id")).orElse(null);

                result.setRace(race);
                result.setDriver(driver);
                result.setPosition(rs.getInt("position"));
                result.setPoints(rs.getInt("points"));
                results.add(result);
            }
            return results;
            } catch (SQLException e) {
                throw new RuntimeException();
        }
    }
}
