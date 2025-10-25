package com.racetalk.dao;

import com.racetalk.entity.RaceResult;

import java.util.List;
import java.util.Optional;

public interface RaceResultDao {
    void create(RaceResult result);

    void update(RaceResult result);

    List<RaceResult> findResultsByDriverNumber(int driverNumber);

    List<RaceResult> findResultsByRaceId(int raceId);

    Optional<RaceResult> findResultsByRaceIdAndDriverNumber(int raceId, int driverNumber);
}
