package com.racetalk.dao.impl;

import com.racetalk.dao.DriverDao;
import com.racetalk.dao.TeamDao;
import com.racetalk.entity.Driver;
import com.racetalk.entity.Team;
import com.racetalk.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DriverDaoImpl implements DriverDao {
    private final Connection connection = DatabaseConnectionUtil.getConnection();
    private final TeamDao teamDao = new TeamDaoImpl();

    @Override
    public void create(Driver driver) {
        String sql = "INSERT INTO drivers (team_id, first_name, last_name, date_of_birth, country) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (driver.getTeam() != null) {
                ps.setInt(1, driver.getTeam().getId());
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setString(2, driver.getFirstName());
            ps.setString(3, driver.getLastName());
            ps.setDate(4, Date.valueOf(driver.getDateOfBirth()));
            ps.setString(5, driver.getCountry());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Driver> findById(int id) {
        String sql = "SELECT * FROM drivers WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(createDriverFromResultSet(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Driver> findAll() {
        String sql = "SELECT * FROM drivers";
        List<Driver> drivers = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                drivers.add(createDriverFromResultSet(rs));
            }
            return drivers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        private Driver createDriverFromResultSet(ResultSet rs) {
            Driver driver = new Driver();
            try {
                driver.setId(rs.getInt("id"));
                int teamId = rs.getInt("team_id");
                if (!rs.wasNull()) {
                    Team team = teamDao.findById(teamId).orElse(null);
                    driver.setTeam(team);
                } else {
                    driver.setTeam(null);
                }

                driver.setFirstName(rs.getString("first_name"));
                driver.setLastName(rs.getString("last_name"));

                Date dob = rs.getDate("date_of_birth");
                if (dob != null) {
                    driver.setDateOfBirth(dob.toLocalDate());
                }

                driver.setCountry(rs.getString("country"));
                return driver;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

