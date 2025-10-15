package com.racetalk.service;

import com.racetalk.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    public List<Team> getAllTeams();

    public Optional<Team> findById(int id);
}
