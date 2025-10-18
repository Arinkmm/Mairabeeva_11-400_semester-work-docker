package com.racetalk.dao;

import com.racetalk.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamDao {
    void create(Team team);

    Optional<Team> findById(int id);

    List<Team> findAll();
}
