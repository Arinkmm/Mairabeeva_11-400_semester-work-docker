package com.racetalk.service;

import com.racetalk.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<Team> getAllTeams();

    Optional<Team> getById(int id);
}
