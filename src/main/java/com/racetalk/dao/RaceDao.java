package com.racetalk.dao;

import com.racetalk.entity.Race;

import java.util.List;
import java.util.Optional;

public interface RaceDao {
    void createPastRace(Race race);

    void updatePastRace(Race race);

    boolean existsPastRaceById(int id);

    List<Race> findUpcomingRaces();

    List<Race> findPastRaces();

    Optional<Race> findBySessionKey(int sessionKey);

    Optional<Race> findPastRaceById(int id);
}
