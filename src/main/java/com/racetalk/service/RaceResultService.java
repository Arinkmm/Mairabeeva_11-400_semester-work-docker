package com.racetalk.service;

import com.racetalk.entity.RaceResult;

import java.util.List;

public interface RaceResultService {
    List<RaceResult> getResultsByRaceId(int raceId);
}
