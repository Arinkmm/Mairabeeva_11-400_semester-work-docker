package com.racetalk.entity;

import java.time.LocalDate;

public class Driver {
    private int id;
    private Team team;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String country;

    public Driver() {}

    public Driver(int id, Team team, String firstName, String lastName, LocalDate dateOfBirth, String country) {
        this.id = id;
        this.team = team;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}
