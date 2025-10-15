package com.racetalk.dao;

import com.racetalk.entity.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverDao {
    public void create(Driver driver);

    public Optional<Driver> findById(int id);

    public List<Driver> findAll();
}
