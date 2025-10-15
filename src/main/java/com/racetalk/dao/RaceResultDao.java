package com.racetalk.dao;

import com.racetalk.entity.RaceResult;

import java.util.List;

public interface RaceResultDao {
    public void create(RaceResult result);

    public void update(RaceResult result);

    public boolean existsById(int id);

    public List<RaceResult> getResultsByRaceId(int raceId);
}
