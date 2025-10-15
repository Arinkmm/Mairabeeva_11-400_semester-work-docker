package com.racetalk.service.impl;

import com.racetalk.dao.RaceResultDao;
import com.racetalk.entity.RaceResult;
import com.racetalk.service.RaceResultService;

import java.util.List;

public class RaceResultServiceImpl implements RaceResultService {
    private final RaceResultDao raceResultDao;

    public RaceResultServiceImpl(RaceResultDao raceResultDao) {
        this.raceResultDao = raceResultDao;
    }

    @Override
    public List<RaceResult> getResultsByRaceId(int raceId) {
        return raceResultDao.getResultsByRaceId(raceId);
    }
}
