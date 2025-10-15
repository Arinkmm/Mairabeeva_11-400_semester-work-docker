package com.racetalk.entity;

public class RaceResult {
    private int id;
    private Race race;
    private Driver driver;
    private int position;
    private int points;

    public RaceResult() {
    }

    public RaceResult(int id, Race race, Driver driver, int position, int points) {
        this.id = id;
        this.race = race;
        this.driver = driver;
        this.position = position;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}