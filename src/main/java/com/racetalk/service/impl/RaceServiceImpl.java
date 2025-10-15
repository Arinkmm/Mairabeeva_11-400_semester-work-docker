package com.racetalk.service.impl;

import com.racetalk.dao.RaceDao;
import com.racetalk.entity.Race;
import com.racetalk.service.RaceService;

import java.util.List;

public class RaceServiceImpl implements RaceService {
    private final RaceDao raceDao;

    public RaceServiceImpl(RaceDao raceDao) {
        this.raceDao = raceDao;
    }

    @Override
    public List<Race> getUpcomingRaces() {
        return raceDao.findUpcomingRaces();
    }
}
