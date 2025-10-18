package com.racetalk.service;

import com.racetalk.entity.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverService {
    List<Driver> getAllDrivers();

    Optional<Driver> getById(int id);
}
