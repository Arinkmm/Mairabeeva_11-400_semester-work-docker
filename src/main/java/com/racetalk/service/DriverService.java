package com.racetalk.service;

import com.racetalk.entity.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverService {
    public List<Driver> getAllDrivers();

    public Optional<Driver> findById(int id);
}
