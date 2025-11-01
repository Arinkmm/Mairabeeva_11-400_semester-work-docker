package com.racetalk.entity;

import java.time.LocalDate;

public class Driver {
    private int driverNumber;
    private Team team;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String country;
    private String photo;

    public Driver() {}

    public Driver(int driverNumber, Team team, String firstName, String lastName, LocalDate dateOfBirth, String country, String photo) {
        this.driverNumber = driverNumber;
        this.team = team;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.photo = photo;
    }

    public Driver(Team team, String firstName, String lastName, LocalDate dateOfBirth, String country, String photo) {
        this.team = team;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.photo = photo;
    }

    public Driver(Team team, String firstName, String lastName, LocalDate dateOfBirth, String country) {
        this.team = team;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
    }

    public int getDriverNumber() { return driverNumber; }
    public void setDriverNumber(int driverNumber) { this.driverNumber = driverNumber; }

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

    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
