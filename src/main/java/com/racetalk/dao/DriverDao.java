package com.racetalk.dao;

import com.racetalk.entity.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverDao {
    void create(Driver driver);

    Optional<Driver> findById(int id);

    List<Driver> findAll();
}
