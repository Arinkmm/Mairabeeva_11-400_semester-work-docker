package com.racetalk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RaceDto {
    private int session_key;
    private String circuit_short_name;
    private String date_start;

    public int getSession_key() { return session_key; }

    public String getCircuit_short_name() { return circuit_short_name; }

    public String getDate_start() { return date_start; }
}
