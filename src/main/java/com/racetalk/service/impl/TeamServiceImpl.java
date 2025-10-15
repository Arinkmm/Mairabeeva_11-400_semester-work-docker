package com.racetalk.service.impl;

import com.racetalk.dao.TeamDao;
import com.racetalk.entity.Team;
import com.racetalk.service.TeamService;

import java.util.List;
import java.util.Optional;

public class TeamServiceImpl implements TeamService {
    private final TeamDao teamDao;

    public TeamServiceImpl(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @Override
    public List<Team> getAllTeams() {
        return teamDao.findAll();
    }

    @Override
    public Optional<Team> findById(int id) {
        return teamDao.findById(id);
    }
}
