package com.racetalk.service.impl;

import com.racetalk.dao.DriverDao;
import com.racetalk.entity.Driver;
import com.racetalk.service.DriverService;

import java.util.List;
import java.util.Optional;

public class DriverServiceImpl implements DriverService {
    private final DriverDao driverDao;

    public DriverServiceImpl(DriverDao driverDao) {
        this.driverDao = driverDao;
    }

    @Override
    public List<Driver> getAllDrivers() {
        return driverDao.findAll();
    }

    @Override
    public Optional<Driver> getById(int id) {
        return driverDao.findById(id);
    }
}
