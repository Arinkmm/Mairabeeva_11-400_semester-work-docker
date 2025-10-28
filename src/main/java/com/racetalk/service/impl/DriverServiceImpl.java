package com.racetalk.service.impl;

import com.racetalk.dao.DriverDao;
import com.racetalk.entity.Driver;
import com.racetalk.exception.DataAccessException;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class DriverServiceImpl implements DriverService {
    private static final Logger logger = LoggerFactory.getLogger(DriverServiceImpl.class);
    private final DriverDao driverDao;

    public DriverServiceImpl(DriverDao driverDao) {
        this.driverDao = driverDao;
    }

    @Override
    public List<Driver> getAllDrivers() {
        try {
            return driverDao.findAll();
        } catch (DataAccessException e) {
            logger.error("Failed to get all drivers", e);
            throw new ServiceException("Could not retrieve drivers", e);
        }
    }

    @Override
    public Optional<Driver> getByDriverNumber(int driverNumber) {
        try {
            return driverDao.findByDriverNumber(driverNumber);
        } catch (DataAccessException e) {
            logger.error("Failed to get driver by number {}", driverNumber, e);
            throw new ServiceException("Could not retrieve driver", e);
        }
    }

    @Override
    public List<Driver> getDriversByTeamId(int teamId) {
        try {
            return driverDao.findDriversByTeamId(teamId);
        } catch (DataAccessException e) {
            logger.error("Failed to get drivers by team id {}", teamId, e);
            throw new ServiceException("Could not retrieve drivers for team", e);
        }
    }
}
