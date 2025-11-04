package com.racetalk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RaceResultDto {
    private int driver_number;
    private int position;
    private int points;

    public int getDriver_number() { return driver_number; }

    public int getPosition() { return position; }

    public int getPoints() { return points; }
}
