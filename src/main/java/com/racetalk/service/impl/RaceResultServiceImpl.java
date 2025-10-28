package com.racetalk.service.impl;

import com.racetalk.dao.RaceResultDao;
import com.racetalk.entity.RaceResult;
import com.racetalk.exception.DataAccessException;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.RaceResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RaceResultServiceImpl implements RaceResultService {
    private static final Logger logger = LoggerFactory.getLogger(RaceResultServiceImpl.class);
    private final RaceResultDao raceResultDao;

    public RaceResultServiceImpl(RaceResultDao raceResultDao) {
        this.raceResultDao = raceResultDao;
    }

    @Override
    public List<RaceResult> getResultsByDriverNumber(int driverNumber) {
        try {
            return raceResultDao.findResultsByDriverNumber(driverNumber);
        } catch (DataAccessException e) {
            logger.error("Failed to get results by driver number {}", driverNumber, e);
            throw new ServiceException("Could not get results by driver number", e);
        }
    }

    @Override
    public List<RaceResult> getResultsByRaceId(int raceId) {
        try {
            return raceResultDao.findResultsByRaceId(raceId);
        } catch (DataAccessException e) {
            logger.error("Failed to get results by race id {}", raceId, e);
            throw new ServiceException("Could not get results by race id", e);
        }
    }

    @Override
    public List<RaceResult> getResultsByTeamId(int teamId) {
        try {
            return raceResultDao.findResultsByTeamId(teamId);
        } catch (DataAccessException e) {
            logger.error("Failed to get results by team id {}", teamId, e);
            throw new ServiceException("Could not get results by team id", e);
        }
    }
}
