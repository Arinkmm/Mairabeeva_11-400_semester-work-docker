package com.racetalk.dao;

import com.racetalk.entity.Race;

import java.util.List;
import java.util.Optional;

public interface RaceDao {
    void create(Race race);

    void update(Race race);

    boolean existsById(int id);

    List<Race> findUpcomingRaces();

    List<Race> findAll();

    Optional<Race> findBySessionKey(int sessionKey);

    Optional<Race> findById(int id);
}
