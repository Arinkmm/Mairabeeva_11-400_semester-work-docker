package com.racetalk.dao;

import com.racetalk.entity.RaceResult;

import java.util.List;

public interface RaceResultDao {
    void create(RaceResult result);

    void update(RaceResult result);

    boolean existsById(int id);

    List<RaceResult> findResultsByRaceId(int raceId);
}
