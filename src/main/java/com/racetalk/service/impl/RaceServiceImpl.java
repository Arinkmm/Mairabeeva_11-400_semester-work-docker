package com.racetalk.service.impl;

import com.racetalk.dao.RaceDao;
import com.racetalk.entity.Race;
import com.racetalk.service.RaceService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RaceServiceImpl implements RaceService {
    private final RaceDao raceDao;

    public RaceServiceImpl(RaceDao raceDao) {
        this.raceDao = raceDao;
    }

    @Override
    public void deleteUpcomingRacesByDate(LocalDate date) {
        raceDao.deleteUpcomingRacesByDate(date);
    }

    @Override
    public List<Race> getUpcomingRaces() {
        return raceDao.findUpcomingRaces();
    }

    @Override
    public List<Race> getPastRaces() {
        return raceDao.findPastRaces();
    }

    @Override
    public Optional<Race> getPastRaceById(int id) {
        return raceDao.findPastRaceById(id);
    }
}
