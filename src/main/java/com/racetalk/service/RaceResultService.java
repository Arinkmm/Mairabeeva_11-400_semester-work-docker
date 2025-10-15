package com.racetalk.service;

import com.racetalk.entity.RaceResult;

import java.util.List;

public interface RaceResultService {
    public List<RaceResult> getResultsByRaceId(int raceId);
}
