package com.racetalk.service.impl;

import com.racetalk.dao.RaceDao;
import com.racetalk.entity.Race;
import com.racetalk.exception.DataAccessException;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.RaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RaceServiceImpl implements RaceService {
    private static final Logger logger = LoggerFactory.getLogger(RaceServiceImpl.class);
    private final RaceDao raceDao;

    public RaceServiceImpl(RaceDao raceDao) {
        this.raceDao = raceDao;
    }

    @Override
    public void deleteUpcomingRacesByDate(LocalDate date) {
        try {
            raceDao.deleteUpcomingRacesByDate(date);
        } catch (DataAccessException e) {
            logger.error("Failed to delete upcoming races by date {}", date, e);
            throw new ServiceException("Could not delete upcoming races by date", e);
        }
    }

    @Override
    public List<Race> getUpcomingRaces() {
        try {
            return raceDao.findUpcomingRaces();
        } catch (DataAccessException e) {
            logger.error("Failed to fetch upcoming races", e);
            throw new ServiceException("Could not fetch upcoming races", e);
        }
    }

    @Override
    public List<Race> getPastRaces() {
        try {
            return raceDao.findPastRaces();
        } catch (DataAccessException e) {
            logger.error("Failed to fetch past races", e);
            throw new ServiceException("Could not fetch past races", e);
        }
    }

    @Override
    public Optional<Race> getPastRaceById(int id) {
        try {
            return raceDao.findPastRaceById(id);
        } catch (DataAccessException e) {
            logger.error("Failed to fetch past race by id {}", id, e);
            throw new ServiceException("Could not fetch past race by id " + id, e);
        }
    }
}
