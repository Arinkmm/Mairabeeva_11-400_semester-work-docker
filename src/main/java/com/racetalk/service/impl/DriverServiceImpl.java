package com.racetalk.service.impl;

import com.cloudinary.Cloudinary;
import com.racetalk.dao.DriverDao;
import com.racetalk.entity.Driver;
import com.racetalk.exception.DataAccessException;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DriverServiceImpl implements DriverService {
    private static final Logger logger = LoggerFactory.getLogger(DriverServiceImpl.class);
    private final DriverDao driverDao;
    private final Cloudinary cloudinary;


    public DriverServiceImpl(DriverDao driverDao, Cloudinary cloudinary) {
        this.driverDao = driverDao;
        this.cloudinary = cloudinary;
    }

    @Override
    public void createDriver(Driver driver, InputStream photoInputStream) {
        Map uploadResult;
        try {
            if (photoInputStream != null && photoInputStream.available() > 0) {
                byte[] photoBytes = photoInputStream.readAllBytes();

                uploadResult = cloudinary.uploader().upload(photoBytes, new HashMap<>());

                String photoUrl = (String) uploadResult.get("secure_url");
                driver.setPhoto(photoUrl);
            }
            driverDao.create(driver);
        } catch (Exception e) {
            throw new ServiceException("Failed to create driver with photo", e);
        }
    }

    @Override
    public void updateDriverTeam(int driverNumber, int newTeam) {
        try {
            driverDao.updateTeam(driverNumber, newTeam);
        } catch (DataAccessException e) {
            logger.error("Failed to update driver team", e);
            throw new ServiceException("Could not update driver team", e);
        }
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
