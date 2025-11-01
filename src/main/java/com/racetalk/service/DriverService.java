package com.racetalk.service;

import com.racetalk.entity.Driver;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface DriverService {
    void createDriver(Driver driver, InputStream photoInputStream);

    void updateDriverTeam(int driverNumber, int newTeam);

    List<Driver> getAllDrivers();

    Optional<Driver> getByDriverNumber(int driverNumber);

    List<Driver> getDriversByTeamId(int teamId);
}
