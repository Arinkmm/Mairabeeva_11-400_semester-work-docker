package com.racetalk.service.impl;

import com.racetalk.dao.TeamDao;
import com.racetalk.entity.Team;
import com.racetalk.exception.DataAccessException;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class TeamServiceImpl implements TeamService {
    private static final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);
    private final TeamDao teamDao;

    public TeamServiceImpl(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @Override
    public List<Team> getAllTeams() {
        try {
            return teamDao.findAll();
        } catch (DataAccessException e) {
            logger.error("Failed to get all teams", e);
            throw new ServiceException("Unable to get all teams", e);
        }
    }

    @Override
    public Optional<Team> getById(int id) {
        try {
            return teamDao.findById(id);
        } catch (DataAccessException e) {
            logger.error("Failed to get team by id {}", id, e);
            throw new ServiceException("Unable to get team by id " + id, e);
        }
    }
}

