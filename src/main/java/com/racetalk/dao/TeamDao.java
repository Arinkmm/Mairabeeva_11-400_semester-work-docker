package com.racetalk.dao;

import com.racetalk.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamDao {
    public void create(Team team);

    public Optional<Team> findById(int id);

    public List<Team> findAll();
}
