package com.racetalk.dao;

import com.racetalk.entity.Race;

import java.util.List;

public interface RaceDao {
    public void create(Race race);

    public void update(Race race);

    public boolean existsById(int id);

    public List<Race> findUpcomingRaces();
}
