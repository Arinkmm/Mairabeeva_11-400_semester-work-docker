package com.racetalk.service;

import com.racetalk.entity.Race;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RaceService {
    void deleteUpcomingRacesByDate(LocalDate date);

    List<Race> getUpcomingRaces();

    List<Race> getPastRaces();

    Optional<Race> getPastRaceById(int id);
}
