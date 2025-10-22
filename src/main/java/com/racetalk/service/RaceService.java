package com.racetalk.service;

import com.racetalk.entity.Race;

import java.util.List;
import java.util.Optional;

public interface RaceService {
    List<Race> getUpcomingRaces();

    List<Race> getPastRaces();

    Optional<Race> getPastRaceById(int id);
}
